package com.gitficko.github.ui.issues_list

import androidx.lifecycle.*
import com.gitficko.github.model.Issue
import com.gitficko.github.remote.CachedClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.stream.Collectors

class IssuesViewModel : ViewModel() {
    private val issues = MutableStateFlow<List<Issue>>(emptyList())
    private val query = MutableStateFlow<String>("")

    val suitableIssues = combine(issues, query) { issues, query ->
        issues.stream().filter { issue ->
            issue.title.startsWith(query)
        }.collect(Collectors.toList())
    }

    fun updateQuery(query: String) {
        this.query.value = query
    }

    fun loadIssuesByToken(token: String) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    CachedClient.getIssues(token)
                }

                withContext(Dispatchers.Main) {
                    issues.value = result
                }
            } catch (e: Exception) {
                issues.value = emptyList()
            } finally {
                query.value = ""
            }
        }
    }
}
