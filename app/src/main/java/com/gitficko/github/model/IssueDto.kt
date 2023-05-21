package com.gitficko.github.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class IssueDto (
    val id: Long,
    val number: Int,
    val title: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String?,
    val repository: RepositoryDto
) {
    fun toEntity(token: String): Issue {
        return Issue(
            id,
            number,
            title,
            createdAt,
            updatedAt,
            repository.owner.login,
            repository.name,
            token
        )
    }
}
