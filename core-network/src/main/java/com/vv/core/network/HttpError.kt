package com.vv.core.network

data class HttpError(
    val statusCode: Int,
    val statusMessage: String?,
    val url: String,
    override val cause: Throwable?
) : Exception(null, cause)