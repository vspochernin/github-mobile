package com.gitficko.github.dao

import androidx.room.Dao
import androidx.room.Ignore
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gitficko.github.model.PullRequest
import io.reactivex.Completable

@Dao
interface PullRequestDao {
    @Query("SELECT * FROM pull_request WHERE pull_request.ownerLogin = :ownerLogin")
    fun getAllByOwnerLogin(ownerLogin: String): List<PullRequest>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(pullRequests: List<PullRequest>)

    @Query("DELETE FROM pull_request WHERE pull_request.ownerLogin = :ownerLogin")
    fun clear(ownerLogin: String)
}