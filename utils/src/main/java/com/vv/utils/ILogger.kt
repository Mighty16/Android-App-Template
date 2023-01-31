package com.vv.utils

interface ILogger {

    fun d(tag: String, message: String)
    fun exception(throwable: Throwable)
    fun warning(message: String, throwable: Throwable? = null)
}