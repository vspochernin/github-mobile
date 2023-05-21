package com.gitficko.github.model

import androidx.room.*

@Entity(tableName = "repository")
class Repository(
    @PrimaryKey
    var id: Int,
    var name: String,
    var fullName: String,
    var private: Boolean,
    @ColumnInfo(name = "created_at")
    var createdAt: String,
    @ColumnInfo(name = "updated_at")
    var updatedAt: String?,
    var language: String?,
    var description: String?,
    var ownerLogin: String,
    var token: String
) {
    constructor() : this(
        0,
        "",
        "",
        false,
        "",
        null,
        null,
        "",
        "",
        ""
    )
}