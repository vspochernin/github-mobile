package com.gitficko.github.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Owner(
    val login: String,
    val id: Int,
    @SerialName(value = "avatar_url")
    val avatarUrl: String,
    @SerialName(value = "html_url")
    val htmlUrl: String
)
