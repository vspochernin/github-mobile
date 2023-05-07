package com.gitficko.github.remote

import com.gitficko.github.model.Owner

class GitHubRep(private val githubApi: GitHubApi) {
    suspend fun getUserInformation(): Owner {
        return githubApi.getCurrentUser()
    }
}