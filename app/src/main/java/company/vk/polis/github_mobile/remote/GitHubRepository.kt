package company.vk.polis.github_mobile.remote

import android.util.Log
import company.vk.polis.github_mobile.model.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GitHubRepository(private val gitHubApi: GitHubApi) {

    fun getRepositories() {
        gitHubApi.getUserRepositories("Bearer ${ApiClient.token}")
            .enqueue(object : Callback<List<Repository>> {
                override fun onResponse(
                    call: Call<List<Repository>>,
                    response: Response<List<Repository>>
                ) {
                    if (response.isSuccessful) {
                        val repositories = response.body()
                        repositories?.let {
                            Log.e("INFO", it.size.toString())
                            for (rep in it) {
                                Log.e("INFO", rep.toString())
                            }
                        }
                    } else {
                        Log.e("ERROR", "response is not successful")
                    }
                }

                override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                    Log.e("ERROR", "some failure")
                }
            })
    }
}