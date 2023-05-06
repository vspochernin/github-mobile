package com.gitficko.github.remote

import com.gitficko.github.model.auth.TokenStorage
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request()
            .addTokenHeader()
            .let { chain.proceed(it) }
    }

    private fun Request.addTokenHeader(): Request {
        val authHeaderName = "Authorization"
        val token = TokenStorage.accessToken
        return newBuilder()
            .apply {
                if (token != null) {
                    header(authHeaderName, "Bearer $token")
                }
            }
            .build()
    }

    private fun String.withBearer() = "Bearer $this"
}