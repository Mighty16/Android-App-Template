package com.vv.core.network

import com.vv.utils.AppError

abstract class ApiError(
    open val httpCode: Int,
    override val cause: Throwable?
) : AppError(cause)

data class DefaultApiError(
    override val httpCode: Int,
    override val cause: Throwable?
) : ApiError(httpCode, cause) {

    override val code: String = "ApiError($httpCode)"

}

data class ConnectionError(override val cause: Throwable?) : AppError(cause) {
    override val code = "ConnectionError"
}

data class UnexpectedError(override val cause: Throwable?) : AppError(cause) {
    override val code = "UnknownError"
}