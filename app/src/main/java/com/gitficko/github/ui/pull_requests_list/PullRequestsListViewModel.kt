package com.gitficko.github.ui.pull_requests_list

import androidx.lifecycle.viewModelScope
import com.gitficko.github.model.PullRequest
import com.gitficko.github.remote.CachedClient
import com.gitficko.github.ui.QueryableListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PullRequestsListViewModel : QueryableListViewModel<PullRequest>() {

    fun loadPullRequestsByOwnerLogin(ownerLogin: String) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    CachedClient.getPullRequestsOf(ownerLogin)
                }

                withContext(Dispatchers.Main) {
                    list.value = result
                }
            } catch (e: Exception) {
                list.value = emptyList()
            } finally {
                query.value = ""
            }
        }
    }

    override fun validateItem(item: PullRequest, query: String): Boolean {
        val queryLowered = query.lowercase()
        return item.title.lowercase().contains(queryLowered) ||
               item.number.toString().contains(queryLowered)
    }
}