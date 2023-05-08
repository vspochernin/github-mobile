package com.gitficko.github.remote

import com.gitficko.github.model.Organization
import com.gitficko.github.model.Owner
import com.gitficko.github.model.PullRequestsInfo
import com.gitficko.github.model.RepositoryRootNode
import com.gitficko.github.model.Starred
import com.gitficko.github.model.RepositoryDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @GET("user")
    suspend fun getCurrentUser(
    ): Owner

    @GET("search/issues")
    suspend fun getPullRequests(
        @Query("q") qString: String
    ): PullRequestsInfo

    @GET("user/orgs")
    suspend fun getOrganizations() : List<Organization>

    @GET("user/starred")
    suspend fun getStarred() : List<Starred>

    @GET("user/repos")
    suspend fun getUserRepositories(
        @Header("Authorization") token: String
    ): List<RepositoryDto>

    @GET("repos/{owner}/{repo}/git/trees/{branch}?recursive=1")
    fun getRepositoryRootNode(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("branch") branch: String
    ): Call<RepositoryRootNode>
}