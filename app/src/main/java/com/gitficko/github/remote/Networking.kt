package com.gitficko.github.remote

import android.content.Context
import com.gitficko.github.model.auth.TokenStorage
import net.openid.appauth.AuthorizationService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import timber.log.Timber

object Networking {

    private var okhttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null

    val githubApi: GitHubApi
        get() = retrofit?.create() ?: error("retrofit is not initialized")

    fun init(context: Context) {
        okhttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor {
                    Timber.tag("Network").d(it)
                }
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .addNetworkInterceptor(AuthInterceptor())
            .addNetworkInterceptor(AuthFailedInterceptor(AuthorizationService(context), TokenStorage))
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okhttpClient!!)
            .build()
    }
}