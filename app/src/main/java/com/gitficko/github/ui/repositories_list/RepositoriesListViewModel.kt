package com.gitficko.github.ui.repositories_list

import androidx.lifecycle.viewModelScope
import com.gitficko.github.model.Repository
import com.gitficko.github.remote.CachedClient
import com.gitficko.github.ui.QueryableListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class RepositoriesListViewModel : QueryableListViewModel<Repository>() {

    fun loadRepositoriesByToken(token: String) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    CachedClient.getRepositories(token)
                }
                Timber.tag("repo_found").i(result.toString())

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

    override fun validateItem(item: Repository, query: String): Boolean {
        return item.fullName.startsWith(query)
    }
}