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
                "Client-ID 2aSJDTUSy2pgP7bR2Sfm9ES0g0yOz-rXyoGays-OPOI"
            )
            .build()
        return chain.proceed(authenticatedRequest)
    }
}