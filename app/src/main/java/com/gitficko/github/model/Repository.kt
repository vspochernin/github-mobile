package com.gitficko.github.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repository")
class Repository(
    @PrimaryKey
    var id: Int,
    var name: String,
    var private: Boolean,
    @ColumnInfo(name = "created_at")
    var createdAt: String,
    @ColumnInfo(name = "updated_at")
    var updatedAt: String?,
    var language: String?,
    var ownerId: Int
) {
    // Пустой конструктор для Room.
    constructor() : this(
        0,
        "",
        false,
        "",
        null,
        "",
        0
    )
}