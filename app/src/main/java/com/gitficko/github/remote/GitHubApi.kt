package com.gitficko.github.remote

import com.gitficko.github.model.*
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GitHubApi {
    @GET("user")
    suspend fun getCurrentUser(
    ): RemoteGithubUser

    @GET("user/repos")
    fun getUserRepositories(
        @Header("Authorization") token: String
    ): Single<List<RepositoryDto>>

    @GET("repos/{owner}/{repo}/git/trees/{branch}?recursive=1")
    fun getRepositoryRootNode(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("branch") branch: String
    ): Call<RepositoryRootNode>

    @GET("repos/{owner}/{repo}/pulls")
    fun getPullRequests(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Single<List<PullRequestDto>>

    @GET("users/{owner}/repos")
    fun getRepositoriesByOwnerName(
        @Path("owner") owner: String
    ): Single<List<RepositoryDto>>
}