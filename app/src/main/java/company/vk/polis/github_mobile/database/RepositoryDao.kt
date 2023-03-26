package company.vk.polis.github_mobile.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import company.vk.polis.github_mobile.model.Repository

@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repositories: List<Repository>)

    @Query("SELECT * FROM repository")
    suspend fun getAllRepositories(): List<Repository>
}
