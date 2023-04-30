package com.gitficko.github.dao

import androidx.room.Dao
import androidx.room.Query
import com.gitficko.github.model.Owner

@Dao
interface OwnerDao {
    @Query("SELECT * FROM owner")
    fun getAll(): List<Owner>
}