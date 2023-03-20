package company.vk.polis.github_mobile.remote

import company.vk.polis.github_mobile.model.Repository
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface GitHubApi {
    @GET("user/repos")
    fun getUserRepositories(
        @Header("Authorization") token: String
    ): Call<List<Repository>>
}