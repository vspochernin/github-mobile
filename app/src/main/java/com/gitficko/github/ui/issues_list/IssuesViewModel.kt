package com.gitficko.github.ui.issues_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gitficko.github.model.Issue
import com.gitficko.github.remote.CachedClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IssuesViewModel : ViewModel() {
    private val issues = MutableLiveData<List<Issue>>()

    val list: LiveData<List<Issue>> get() = issues

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
            }
        }
    }
}
