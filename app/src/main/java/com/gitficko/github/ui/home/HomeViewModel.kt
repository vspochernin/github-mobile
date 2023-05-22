package com.gitficko.github.ui.home

import android.app.Application
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gitficko.github.model.RemoteGithubUser
import com.gitficko.github.model.Repository
import com.gitficko.github.model.auth.AuthRepository
import com.gitficko.github.remote.ApiClient.gitHubApi
import com.gitficko.github.remote.CachedClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.openid.appauth.AuthorizationService
import timber.log.Timber

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val authService: AuthorizationService = AuthorizationService(getApplication())

    private val authRepository = AuthRepository()

    private val loadingMutableStateFlow = MutableStateFlow(false)
    private val userInfoMutableStateFlow = MutableStateFlow<RemoteGithubUser?>(null)
    private val toastEventChannel = Channel<Int>(Channel.BUFFERED)
    private val logoutPageEventChannel = Channel<Intent>(Channel.BUFFERED)
    private val logoutCompletedEventChannel = Channel<Unit>(Channel.BUFFERED)


    val loadingFlow: Flow<Boolean>
        get() = loadingMutableStateFlow.asStateFlow()

    val userInfoFlow: Flow<RemoteGithubUser?>
        get() = userInfoMutableStateFlow.asStateFlow()

    val toastFlow: Flow<Int>
        get() = toastEventChannel.receiveAsFlow()

    val logoutPageFlow: Flow<Intent>
        get() = logoutPageEventChannel.receiveAsFlow()

    val logoutCompletedFlow: Flow<Unit>
        get() = logoutCompletedEventChannel.receiveAsFlow()


    fun logout() {
        val customTabsIntent = CustomTabsIntent.Builder().build()

        val logoutPageIntent = authService.getEndSessionRequestIntent(
            authRepository.getEndSessionRequest(),
            customTabsIntent
        )

        logoutPageEventChannel.trySendBlocking(logoutPageIntent)
    }

    fun webLogoutComplete() {
        authRepository.logout()
        logoutCompletedEventChannel.trySendBlocking(Unit)
    }

    private var currentQuery: String? = null

    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>>
        get() = _repositories

    fun searchRepositories(query: String) {
        currentQuery = query
        viewModelScope.launch {
//            val response = gitHubApi.searchRepositories(query)
//            if (response.isSuccessful) {
//                _repositories.postValue((response.body()?.items ?: emptyList()) as List<Repository>?)
//            } else {
//                Timber.e(response.errorBody()?.string() ?: "Unknown error")
//            }
            try {
                val result = withContext(Dispatchers.IO) {
                    CachedClient.searchRepositories(query)
                }
                Timber.tag("repo_found").i(result.toString())

                withContext(Dispatchers.Main) {
                    _repositories.value = result
                }
            } catch (e: Exception) {
                Timber.e(e)
                _repositories.value = emptyList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }
}