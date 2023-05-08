package com.gitficko.github.remote

import com.gitficko.github.model.auth.TokenStorage
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

object ApiClient {
    val token = TokenStorage.accessToken //TODO: Вынести token в DataStore

    private const val BASE_URL = "https://api.github.com/"

    private val okHttpClient = OkHttpClient.Builder().build()

    private val jsonConf = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(jsonConf.asConverterFactory("application/json".toMediaType()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    val gitHubApi: GitHubApi = retrofit.create(GitHubApi::class.java)
}