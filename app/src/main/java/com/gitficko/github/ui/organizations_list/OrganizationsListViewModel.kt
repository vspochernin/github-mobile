package com.gitficko.github.ui.organizations_list

import androidx.lifecycle.viewModelScope
import com.gitficko.github.model.Organization
import com.gitficko.github.remote.CachedClient
import com.gitficko.github.ui.QueryableListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrganizationsListViewModel : QueryableListViewModel<Organization>() {

    fun loadOrganizationsByToken(token: String) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    CachedClient.getOrganizations(token)
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

    override fun validateItem(item: Organization, query: String): Boolean {
        return item.login.startsWith(query)
    }
}