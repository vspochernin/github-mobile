package company.vk.polis.github_mobile.remote

import company.vk.polis.github_mobile.model.RepositoryRootNode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GitHubRepositoryRootNode(
    private val gitHubApi: GitHubApi,
    private val owner: String,
    private val repo: String,
    private val branch: String
) {

    fun getNodes(onSuccess: (RepositoryRootNode) -> Unit, onError: (Throwable) -> Unit) {
        gitHubApi.getRepositoryRootNode(owner, repo, branch)
            .enqueue(object : Callback<RepositoryRootNode> {
                override fun onResponse(
                    call: Call<RepositoryRootNode>,
                    response: Response<RepositoryRootNode>
                ) {
                    if (response.isSuccessful) {
                        val treeNode = response.body()
                        treeNode?.let {
                            onSuccess(it)
                        }
                    } else {
                        onError(Exception("Failed to get repository root node of owner: ${owner}, repo: ${repo}, branch: ${branch}"))
                    }
                }

                override fun onFailure(call: Call<RepositoryRootNode>, t: Throwable) {
                    onError(t)
                }
            })
    }
}