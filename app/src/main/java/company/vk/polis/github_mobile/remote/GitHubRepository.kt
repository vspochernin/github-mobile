package company.vk.polis.github_mobile.remote

import android.util.Log
import company.vk.polis.github_mobile.database.RepositoryDao
import company.vk.polis.github_mobile.model.Repository

class GitHubRepository(private val gitHubApi: GitHubApi, private val repositoryDao: RepositoryDao) {

    suspend fun getRepositories() {
        val response = gitHubApi.getUserRepositories("Bearer ${ApiClient.token}").execute()

        if (response.isSuccessful) {
            val repositories = response.body()
            repositories?.let {
                repositoryDao.insertRepositories(it)
            }
        } else {
            Log.e("ERROR", "response is not successful")
        }
    }
    suspend fun getAllRepositories(): List<Repository> {
        return repositoryDao.getAllRepositories()
    }
}