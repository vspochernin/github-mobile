package company.vk.polis.github_mobile.remote

import company.vk.polis.github_mobile.model.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GitHubRepository(private val gitHubApi: GitHubApi) {

    fun getRepositories(onSuccess: (List<Repository>) -> Unit, onError: (Throwable) -> Unit) {
        gitHubApi.getUserRepositories("Bearer ${ApiClient.token}")
            .enqueue(object : Callback<List<Repository>> {
                override fun onResponse(
                    call: Call<List<Repository>>,
                    response: Response<List<Repository>>
                ) {
                    if (response.isSuccessful) {
                        val repositories = response.body()
                        repositories?.let {
                            onSuccess(it)
                        }
                    } else {
                        onError(Exception("Failed to get repositories"))
                    }
                }

                override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                    onError(t)
                }
            })
    }
}