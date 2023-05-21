package com.gitficko.github.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gitficko.github.model.Repository

@Dao
interface RepositoryDao {
    @Query("SELECT * FROM repository WHERE repository.token = :token")
    fun getAllByToken(token: String): List<Repository>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(repositories: List<Repository>)

    @Query("DELETE FROM repository WHERE repository.token = :token")
    fun clear(token: String)
}