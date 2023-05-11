package com.gitficko.github.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gitficko.github.model.RepositoryDto
import com.gitficko.github.remote.GitHubApi
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchViewModel(private val gitHubApi: GitHubApi) : ViewModel() {

    private val _searchResults = MutableLiveData<List<RepositoryDto>>()
    val searchResults: LiveData<List<RepositoryDto>> = _searchResults

    fun search(query: String) {
        viewModelScope.launch {
            val response = gitHubApi.searchRepositories(query)
            if (response.isSuccessful) {
                _searchResults.value = response.body()?.items
            } else {
                Timber.e("Error: ${response.code()} ${response.message()}")
            }
        }
    }
}