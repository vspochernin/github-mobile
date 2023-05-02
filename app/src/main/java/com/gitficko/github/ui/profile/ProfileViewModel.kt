package com.gitficko.github.ui.profile

import androidx.lifecycle.ViewModel
import com.gitficko.github.model.Owner
import com.gitficko.github.remote.ApiClient
import com.gitficko.github.remote.GitHubRep
import com.gitficko.github.remote.Networking

class ProfileViewModel : ViewModel() {
    var user: Owner? = null
    private val gitHubRep = GitHubRep(Networking.githubApi)


    suspend fun loadUser() {
        val currentUser = gitHubRep.getUserInformation()
        user = Owner(
            id = currentUser.id,
            login = currentUser.login,
            avatarUrl = currentUser.name,
            htmlUrl = currentUser.name
        )
    }
}