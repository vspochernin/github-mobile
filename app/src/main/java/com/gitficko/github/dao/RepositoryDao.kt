package com.gitficko.github.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gitficko.github.model.Repository

@Dao
interface RepositoryDao {
    @Query("SELECT * FROM repository WHERE repository.ownerLogin = :ownerLogin")
    fun getAllByOwnerLogin(ownerLogin: String): List<Repository>

    @Insert
    fun insert(repositories: List<Repository>)

    @Query("DELETE FROM repository WHERE repository.ownerLogin = :ownerLogin")
    fun clear(ownerLogin: String)
}