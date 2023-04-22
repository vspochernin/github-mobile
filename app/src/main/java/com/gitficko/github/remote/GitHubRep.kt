package com.gitficko.github.remote

import com.gitficko.github.model.RemoteGithubUser

class GitHubRep {
    suspend fun getUserInformation(): RemoteGithubUser {
        return Networking.githubApi.getCurrentUser()
    }
}