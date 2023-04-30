package com.gitficko.github.dao

import androidx.room.Dao
import androidx.room.Query
import com.gitficko.github.model.PullRequest

@Dao
interface PullRequestDao {
    @Query("SELECT pull_request.* FROM repository JOIN pull_request ON pull_request.repositoryId = :repositoryId")
    fun getAllPullRequestsByRepositoryId(repositoryId: Int): List<PullRequest>
}