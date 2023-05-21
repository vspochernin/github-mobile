package com.gitficko.github.ui.issues_list

import androidx.lifecycle.viewModelScope
import com.gitficko.github.model.Issue
import com.gitficko.github.remote.CachedClient
import com.gitficko.github.ui.QueryableListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IssuesListViewModel : QueryableListViewModel<Issue>() {

    fun loadIssuesByToken(token: String) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    CachedClient.getIssues(token)
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

    override fun validateItem(item: Issue, query: String): Boolean {
        return item.title.startsWith(query)
    }
}