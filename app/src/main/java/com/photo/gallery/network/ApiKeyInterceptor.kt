package com.photo.gallery.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .addHeader(
                "Authorization",
                "Bearer fCrhylAXN5cGGMpGzgPr3VzruVV9UcHxL8S1I6dOFxA"
            )
            .build()
        return chain.proceed(authenticatedRequest)
    }
}