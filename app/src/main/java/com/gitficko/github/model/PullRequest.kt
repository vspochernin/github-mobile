package com.gitficko.github.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pull_request")
class PullRequest(
    @PrimaryKey
    var id: Int,
    var title: String,
    var number: Int,
    var createdAt: String,
    var updatedAt: String?,
    var ownerLogin: String
) {
    constructor(): this(
        0,
        "",
        0,
        "",
        null,
        ""
    )
}
