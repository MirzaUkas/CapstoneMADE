package com.mirdev.capstone.core.utils

import com.mirdev.capstone.core.BuildConfig.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class CustomInterceptors : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val url = original.url.newBuilder().addQueryParameter("api_key", API_KEY).build()
        val requestBuilder = original.newBuilder().url(url).build()
        return chain.proceed(requestBuilder)
    }
}