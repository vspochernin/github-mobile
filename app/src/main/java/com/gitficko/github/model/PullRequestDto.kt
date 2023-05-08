package com.gitficko.github.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PullRequestDto (
    val id: Int,
    val title: String,
    val number: Int,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String?,
    val user: User
) {
    fun toEntity(): PullRequest {
        return PullRequest(
            id,
            title,
            number,
            createdAt,
            updatedAt,
            user.login
        )
    }
}
