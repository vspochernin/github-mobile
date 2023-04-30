package com.gitficko.github.dao

import androidx.room.Dao
import androidx.room.Query
import com.gitficko.github.model.Repository

@Dao
interface RepositoryDao {
    @Query("SELECT repository.* FROM owner JOIN repository ON repository.ownerId = :ownerId")
    fun getAllRepositoriesByOwnerId(ownerId: Int): List<Repository>
}