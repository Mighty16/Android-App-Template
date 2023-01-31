package com.vv.utils

abstract class AppError(override val cause: Throwable?) : Throwable(cause) {
    abstract val code: String
}