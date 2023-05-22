package com.gitficko.github.remote

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gitficko.github.dao.*
import com.gitficko.github.model.*

@Database(entities = [Owner::class, Repository::class, PullRequest::class, Issue::class, Organization::class], version = 22)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun ownerDao(): OwnerDao
    abstract fun repositoryDao(): RepositoryDao
    abstract fun pullRequestDao(): PullRequestDao
    abstract fun issueDao(): IssueDao
    abstract fun organizationDao(): OrganizationDao
}