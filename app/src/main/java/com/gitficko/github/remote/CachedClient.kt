package com.gitficko.github.remote

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.gitficko.github.model.*
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
            Log.e("pull_requests_list_fetching_error", e.stackTraceToString())
            return database!!.pullRequestDao().getAllByOwnerLogin(ownerLogin)
        }
    }
}