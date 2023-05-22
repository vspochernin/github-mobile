package com.gitficko.github.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class OrganizationDto(
    val id: Int,
    val login: String,
    @SerialName(value = "avatar_url")
    val avatarUrl: String?,
    val description: String
) {
    fun toEntity(ownerLogin: String): Organization {
        return Organization(
            id,
            login,
            avatarUrl,
            description,
            ownerLogin
        )
    }
}
