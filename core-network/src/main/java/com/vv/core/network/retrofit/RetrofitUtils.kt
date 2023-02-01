package com.vv.core.network.retrofit

import com.vv.core.network.IApiErrorParser
import com.vv.core.network.retrofit.try_adapter.TryAdapterFactory
import com.vv.utils.AppError
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit

fun <T> createRetrofitService(
    baseUrl: String,
    serviceClass: Class<T>,
    errorParser: IApiErrorParser<HttpException, AppError> = DefaultRetrofitErrorParser,
    okhttpClient: OkHttpClient? = null
): T {
    val retrofitBuilder = Retrofit.Builder()
        .addCallAdapterFactory(TryAdapterFactory(errorParser))
        .baseUrl(baseUrl)
    okhttpClient?.let {
        retrofitBuilder.client(okhttpClient)
    }
    return retrofitBuilder.build().create(serviceClass)
}

