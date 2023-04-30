package com.gitficko.github.remote

import com.gitficko.github.model.RepositoryDto

class GitHubRepository(private val gitHubApi: GitHubApi) {

    fun getRepositories(onSuccess: (List<RepositoryDto>) -> Unit, onError: (Throwable) -> Unit) {
//        gitHubApi.getUserRepositories("Bearer ${ApiClient.token}")
//            .enqueue(object : Callback<List<Repository>> {
//                override fun onResponse(
//                    call: Call<List<Repository>>,
//                    response: Response<List<Repository>>
//                ) {
//                    if (response.isSuccessful) {
//                        val repositories = response.body()
//                        repositories?.let {
//                            onSuccess(it)
//                        }
//                    } else {
//                        onError(Exception("Failed to get repositories"))
//                    }
//                }
//
//                override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
//                    onError(t)
//                }
//            })
    }

}