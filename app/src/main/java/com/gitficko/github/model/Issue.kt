package com.gitficko.github.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "issue")
class Issue (
    @PrimaryKey
    var id: Long,
    var number: Int,
    var title: String,
    var createdAt: String,
    var updatedAt: String?,
    var ownerLogin: String,
    var repositoryName: String,
    var token: String
) {
    constructor() : this(
        0,
        0,
        "",
        "",
        "",
        "",
        "",
        ""
    )
}