package com.gitficko.github.remote

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gitficko.github.dao.OwnerDao
import com.gitficko.github.dao.PullRequestDao
import com.gitficko.github.dao.RepositoryDao
import com.gitficko.github.model.Owner
import com.gitficko.github.model.PullRequest
import com.gitficko.github.model.Repository

@Database(entities = [Owner::class, Repository::class, PullRequest::class], version = 5)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun ownerDao(): OwnerDao
    abstract fun repositoryDao(): RepositoryDao
    abstract fun pullRequestDao(): PullRequestDao
}