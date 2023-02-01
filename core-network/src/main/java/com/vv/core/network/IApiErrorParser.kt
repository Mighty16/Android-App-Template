package com.vv.core.network

import com.vv.utils.AppError

interface IApiErrorParser<IN, OUT : AppError> {

    fun parseError(url: String, error: IN): OUT
}