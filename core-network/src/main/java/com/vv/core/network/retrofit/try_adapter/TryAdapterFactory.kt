package com.vv.core.network.retrofit.try_adapter

import com.vv.core.network.IApiErrorParser
import com.vv.utils.AppError
import com.vv.utils.Try
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class TryAdapterFactory(private val errorParser: IApiErrorParser<HttpException, AppError>) :
    CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val rawReturnType = getRawType(returnType)
        if (rawReturnType != Call::class.java) return null
        if (returnType !is ParameterizedType) return null

        val innerType = getParameterUpperBound(0, returnType)
        if (getRawType(innerType) != Try::class.java) return null

        if (innerType !is ParameterizedType) return TryCallAdapter<Nothing>(
            errorParser,
            Nothing::class.java
        )

        val resultType = getParameterUpperBound(0, innerType)
        return TryCallAdapter<Any?>(errorParser, resultType)

    }

    private class TryCallAdapter<R>(
        private val errorParser: IApiErrorParser<HttpException, AppError>,
        private val type: Type
    ) : CallAdapter<R, Call<Try<R>>> {

        override fun responseType(): Type = type

        override fun adapt(call: Call<R>): Call<Try<R>> = TryCall(errorParser, call)
    }
}