package com.gitficko.github.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object ApiClient {
    const val token = "" //TODO: Вынести token в DataStore

    private const val BASE_URL = "https://api.github.com/"

    private val okHttpClient = OkHttpClient.Builder().build()

    private val jsonConf = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(jsonConf.asConverterFactory("application/json".toMediaType()))
        .client(okHttpClient)
        .build()

    val gitHubApi: GitHubApi = retrofit.create(GitHubApi::class.java)
}