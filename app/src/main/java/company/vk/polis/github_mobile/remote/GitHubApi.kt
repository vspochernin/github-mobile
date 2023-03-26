package company.vk.polis.github_mobile.remote

import company.vk.polis.github_mobile.model.Repository
import company.vk.polis.github_mobile.model.RepositoryRootNode
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GitHubApi {
    @GET("user/repos")
    fun getUserRepositories(
        @Header("Authorization") token: String
    ): Call<List<Repository>>

    @GET("repos/{owner}/{repo}/git/trees/{branch}?recursive=1")
    fun getRepositoryRootNode(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("branch") branch: String
    ): Call<RepositoryRootNode>
}