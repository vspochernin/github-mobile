package com.gitficko.github.remote

import android.app.Application
import androidx.room.Room
import com.gitficko.github.model.*
import timber.log.Timber
import java.io.IOException
import java.util.stream.Collectors

object CachedClient {
    var database: ApplicationDatabase? = null

    fun init(context: Application) {
        this.database = Room.databaseBuilder(
            context,
            ApplicationDatabase::class.java,
            "application_database"
        ).build()
    }

    suspend fun getPullRequestsOf(ownerLogin: String): List<PullRequest> {
        try {
            val pullRequestsInfo = Networking.githubApi.getPullRequests("state:open author:$ownerLogin type:pr")
            val pullRequests = pullRequestsInfo.items
                .stream()
                .map(PullRequestDto::toEntity)
                .collect(Collectors.toList())
            database!!.pullRequestDao().clear(ownerLogin)
            database!!.pullRequestDao().insert(pullRequests)
            return pullRequests
        } catch (e: IOException) {
            Timber.e("pull_requests_list_fetching_error", e.stackTraceToString())
            return database!!.pullRequestDao().getAllByOwnerLogin(ownerLogin)
        }
    }

    suspend fun getIssues(token: String): List<Issue> {
        try {
            val issues = Networking.githubApi.getIssues("Bearer $token")
                .stream()
                .map { issueDto -> issueDto.toEntity(token) }
                .collect(Collectors.toList())
            database!!.issueDao().clear(token)
            database!!.issueDao().insert(issues)
            return issues
        } catch (e: IOException) {
            Timber.e("issues_list_fetching_error", e.stackTraceToString())
            return database!!.issueDao().getAllByToken(token)
        }
    }

    suspend fun getRepositories(token: String): List<Repository> {
        try {
            val repositories = Networking.githubApi.getUserRepositories("Bearer $token")
                .stream()
                .map { repositoryDto -> repositoryDto.toEntity(token) }
                .collect(Collectors.toList())
            Timber.tag("repositories_fetched_successfully").i(repositories.toString())
            database!!.repositoryDao().clear(token)
            database!!.repositoryDao().insert(repositories)
            return repositories
        } catch (e: IOException) {
            Timber.tag("repositories_fetching_error").e(e.stackTraceToString())
            return database!!.repositoryDao().getAllByToken(token)
        }
    }

    suspend fun getOrganizations(token: String): List<Organization> {
        try {
            val organizations = Networking.githubApi.getOrganizations("Bearer $token")
                .stream()
                .map { organizationDto -> organizationDto.toEntity(token) }
                .collect(Collectors.toList())
            Timber.tag("organizations_fetched_successfully").i(organizations.toString())
            database!!.organizationDao().clear(token)
            database!!.organizationDao().insert(organizations)
            return organizations
        } catch (e: IOException) {
            Timber.tag("organizations_fetching_error").e(e.stackTraceToString())
            return database!!.organizationDao().getAllByToken(token)
        }
    }
}