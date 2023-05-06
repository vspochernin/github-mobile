package com.gitficko.github.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "owner")
@Serializable
class Owner(
    @PrimaryKey
    var id: Int,
    @SerialName("avatar_url")
    var avatarUrl: String,
    @SerialName("html_url")
    var htmlUrl: String,
    var login: String,
    var name: String?,
    var location: String?,
    var bio: String?,
)
