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
    var login: String,
    var name: String?,
    var location: String?,
    var bio: String?,
    @SerialName(value = "avatar_url")
    var avatarUrl: String,
    @SerialName(value = "html_url")
    var htmlUrl: String
) {
    // Пустой конструктор для Room.
    constructor() : this(
        id = 0,
        login = "",
        name = null,
        location = null,
        bio = null,
        avatarUrl = "",
        htmlUrl = ""
    )

    override fun toString(): String {
        return "Owner(id=$id, login='$login', name='$name', location='$location', bio='$bio', avatarUrl='$avatarUrl', htmlUrl='$htmlUrl')"
    }
}
