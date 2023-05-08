package com.gitficko.github.ui.profile

import androidx.lifecycle.ViewModel
import com.gitficko.github.model.Owner
import com.gitficko.github.remote.GitHubRep
import com.gitficko.github.remote.Networking
import com.gitficko.github.remote.UserInfo

class ProfileViewModel : ViewModel() {
    var user: Owner? = null
    var organizationsCount: Int = -1
    var starredCount: Int = -1
    private val gitHubRep = GitHubRep(Networking.githubApi)
    private val userInfo = UserInfo(Networking.githubApi)

    suspend fun loadUser() {
        val currentUser = gitHubRep.getUserInformation()
        user = Owner(
            id = currentUser.id,
            login = currentUser.login,
            name = currentUser.name,
            location = currentUser.location,
            bio = currentUser.bio,
            followers = currentUser.followers,
            avatarUrl = currentUser.avatarUrl,
            htmlUrl = currentUser.htmlUrl
        )
        organizationsCount = userInfo.getOrganizationsCount()
        starredCount = userInfo.getStarredCount()
    }
}