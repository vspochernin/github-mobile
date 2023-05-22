package com.gitficko.github.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity(tableName = "organization")
data class Organization(
    @PrimaryKey
    var id: Int,
    var login: String,
    @SerialName(value = "avatar_url")
    var avatarUrl: String?,
    var description: String?,
    var token: String
)
