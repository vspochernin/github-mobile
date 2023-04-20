package com.gitficko.github.model

import kotlinx.serialization.Serializable

@Serializable
data class RemoteGithubUser(
    val id: Long,
    val login: String,
    val name: String,
)
