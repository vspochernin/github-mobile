package company.vk.polis.github_mobile.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    const val token = "" //TODO: Вынести token в DataStore

    private const val BASE_URL = "https://api.github.com/"

    private val okHttpClient = OkHttpClient.Builder().build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val gitHubApi: GitHubApi = retrofit.create(GitHubApi::class.java)
}