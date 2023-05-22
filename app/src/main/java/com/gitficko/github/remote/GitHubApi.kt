package com.gitficko.github.remote

import com.gitficko.github.model.*
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

    @GET("/issues")
    suspend fun getIssues(
        @Header("Authorization") authorizationHeader: String
    ): List<IssueDto>

    @GET("user/orgs")
    suspend fun getOrganizations(
        @Header("Authorization") authorizationHeader: String
    ): List<OrganizationDto>

    @GET("users/{owner}/orgs")
    suspend fun getOrganizationsOf(
        @Path("owner") owner: String
    ): List<OrganizationDto>

    @GET("user/starred")
    suspend fun getStarred(): List<Starred>

    @GET("user/repos")
    suspend fun getUserRepositories(
        @Header("Authorization") token: String
    ): List<RepositoryDto>

    @GET("user/starred")
    suspend fun getUserStarred(
        @Header("Authorization") token: String
    ): List<RepositoryDto>

    @GET("repos/{owner}/{repo}/git/trees/{branch}?recursive=1")
    fun getRepositoryRootNode(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("branch") branch: String
    ): Call<RepositoryRootNode>

    @GET("repos/{owner}/{repo}/contents/{path}")
    suspend fun getRepositoryContents(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("path") path: String,
        @Query("ref") branch: String? = null
    ): List<ContentDto>

    @GET("repos/{owner}/{repo}/contents/README.md")
    suspend fun getReadme(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): ContentDto
}