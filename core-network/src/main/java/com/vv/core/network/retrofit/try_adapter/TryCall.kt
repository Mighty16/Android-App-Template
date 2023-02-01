package com.vv.core.network.retrofit.try_adapter

import com.vv.core.network.ApiError
import com.vv.core.network.ConnectionError
import com.vv.core.network.HttpError
import com.vv.core.network.IApiErrorParser
import com.vv.core.network.UnexpectedError
import com.vv.utils.AppError
import com.vv.utils.Try
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

internal class TryCall<T>(
    private val errorParser: IApiErrorParser<HttpException, AppError>,
    proxy: Call<T>
) :
    CallDelegate<T, Try<T>>(proxy) {

    override fun enqueueImpl(callback: Callback<Try<T>>) {
        proxy.enqueue(TryCallback(errorParser, this, callback))
    }

    override fun cloneImpl(): Call<Try<T>> {
        return TryCall(errorParser, proxy.clone())
    }

    override fun timeout(): Timeout {
        return proxy.timeout()
    }

    private class TryCallback<T>(
        private val errorParser: IApiErrorParser<HttpException, AppError>,
        private val proxy: TryCall<T>,
        private val callback: Callback<Try<T>>
    ) : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            val result = if (response.isSuccessful) {
                val value = response.body() as T
                Try.Success(value)
            } else {
                val errorCause = HttpError(
                    statusCode = response.code(),
                    statusMessage = response.message(),
                    url = call.request().url().toString(),
                    cause = null
                )
                val apiError = UnexpectedError(errorCause)
                Try.Error(apiError)
            }
            callback.onResponse(proxy, Response.success(result))
        }

        override fun onFailure(call: Call<T>, error: Throwable) {
            val appError = when (error) {
                is HttpException -> errorParser.parseError(call.request().url().toString(), error)
                is IOException -> ConnectionError(error)
                else -> UnexpectedError(error)
            }
            callback.onResponse(proxy, Response.success(Try.Error(appError)))
        }
    }
}