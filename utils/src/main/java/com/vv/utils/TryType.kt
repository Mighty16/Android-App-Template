package com.vv.utils

sealed interface Try<out T> {
    data class Success<T>(val data: T) : Try<T>
    data class Error(val error: AppError) : Try<Nothing>
}