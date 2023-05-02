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

    @Query("SELECT * FROM pull_request WHERE pull_request.ownerLogin = :ownerLogin AND pull_request.repositoryName = :repositoryName")
    fun getAllByOwnerLoginAndRepositoryName(ownerLogin: String, repositoryName: String): List<PullRequest>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(pullRequests: List<PullRequest>)

    @Query("DELETE FROM pull_request WHERE pull_request.ownerLogin = :ownerLogin AND pull_request.repositoryName = :repositoryName")
    fun clear(ownerLogin: String, repositoryName: String)
}