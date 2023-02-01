package com.vv.core.network.retrofit

import com.vv.core.network.DefaultApiError
import com.vv.core.network.HttpError
import com.vv.core.network.IApiErrorParser
import com.vv.utils.AppError
import retrofit2.HttpException

object DefaultRetrofitErrorParser : IApiErrorParser<HttpException, AppError> {

    override fun parseError(url: String, error: HttpException): AppError {
        val httpError = HttpError(
            statusCode = error.code(),
            statusMessage = error.message(),
            url = url,
            cause = null
        )
        return DefaultApiError(httpError.statusCode, httpError)
    }
}