package com.gitficko.github.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gitficko.github.model.Issue

@Dao
interface IssueDao {
    @Query("SELECT * FROM issue WHERE issue.token = :token")
    fun getAllByToken(token: String): List<Issue>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(issues: List<Issue>)

    @Query("DELETE FROM issue WHERE issue.token = :token")
    fun clear(token: String)
}