package com.gitficko.github.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "owner")
@Serializable
data class Owner(
    @PrimaryKey
    var id: Long,
    var login: String,
    var name: String?,
    var location: String?,
    var bio: String?,
    var followers: Int,
    @SerialName(value = "avatar_url")
    var avatarUrl: String,
    @SerialName(value = "html_url")
    var htmlUrl: String,
)
