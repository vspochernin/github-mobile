package com.gitficko.github.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gitficko.github.model.Organization
import com.gitficko.github.model.Repository

@Dao
interface OrganizationDao {
    @Query("SELECT * FROM organization WHERE organization.token = :token")
    fun getAllByToken(token: String): List<Organization>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(repositories: List<Organization>)

    @Query("DELETE FROM organization WHERE organization.token = :token")
    fun clear(token: String)
}