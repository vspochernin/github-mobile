package com.gitficko.github.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "owner")
@Serializable
class Owner(
    @PrimaryKey
    val id: Long,
    val login: String,
    val name: String,
    val location: String?,
    val bio: String?,
    @SerialName(value = "avatar_url")
    val avatarUrl: String,
    @SerialName(value = "html_url")
    val htmlUrl: String
) {
    // Пустой конструктор для Room.
    constructor() : this(
        id = 0,
        login = "",
        name = "",
        location = "",
        bio = "",
        avatarUrl = "",
        htmlUrl = ""
    )

    override fun toString(): String {
        return "Owner(id=$id, login='$login', name='$name', location='$location', bio='$bio', avatarUrl='$avatarUrl', htmlUrl='$htmlUrl')"
    }
}
