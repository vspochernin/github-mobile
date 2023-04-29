package com.gitficko.github.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PullRequest (
    val id: Long,
    val title: String,
    val number: Int,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String?,
    val head: Head
)
