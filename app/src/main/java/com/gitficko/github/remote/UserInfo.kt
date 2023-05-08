package com.gitficko.github.remote

class UserInfo(private val githubApi: GitHubApi) {
    suspend fun getOrganizationsCount() : Int {
        return githubApi.getOrganizations().size
    }

    suspend fun getStarredCount() : Int {
        return githubApi.getStarred().size
    }
}