package com.gitficko.github.ui.home

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.gitficko.github.R
import com.gitficko.github.databinding.FragmentHomeBinding
import com.gitficko.github.model.CurrentUserPreferencesKey
import com.gitficko.github.model.Repository
import com.gitficko.github.model.SharedPreferencesKey
import com.gitficko.github.remote.ApiClient
import com.gitficko.github.remote.Networking
import com.gitficko.github.remote.Networking.githubApi
import com.gitficko.github.ui.repositories_list.RepositoriesListAdapter
import com.gitficko.github.ui.repositories_list.RepositoryClickListener
import com.gitficko.github.utils.launchAndCollectIn
import com.gitficko.github.utils.resetNavGraph
import com.gitficko.github.utils.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber


class HomeFragment : Fragment(), RepositoryClickListener {

    private val viewModel: HomeViewModel by viewModels()
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var recyclerView: RecyclerView

    private val logoutResponse = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.webLogoutComplete()
        } else {
            viewModel.webLogoutComplete()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        toolbar.title = resources.getString(R.string.title_home)
        toolbar.inflateMenu(R.menu.menu_search)

        val searchItem = toolbar.menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchRepositories(query.orEmpty())
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchRepositories(newText.orEmpty())
                return false
            }
        })

        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                searchView.setOnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                        searchRepositories(searchView.query.toString())
                        searchView.clearFocus()
                        true
                    } else {
                        false
                    }
                }
            } else {
                searchView.setOnKeyListener(null)
            }
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            val currentUser = Networking.githubApi.getCurrentUser()
            val sharedPreferences = context!!.getSharedPreferences(
                SharedPreferencesKey.CURRENT_USER.value,
                Context.MODE_PRIVATE
            )
            val sharedPreferencesEditor = sharedPreferences.edit()
            sharedPreferencesEditor.putString(
                CurrentUserPreferencesKey.LOGIN.value, currentUser.login
            ).apply()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)

        binding.getUserRep.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_repositories)
        }
        binding.getUserPr.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_pull_requests)
        }
        binding.logout.setOnClickListener {
            viewModel.logout()
        }

        viewModel.loadingFlow.launchAndCollectIn(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.getUserRep.isEnabled = !isLoading
            binding.userInfo.isVisible = !isLoading
        }

        viewModel.userInfoFlow.launchAndCollectIn(viewLifecycleOwner) { userInfo ->
            binding.userInfo.text = userInfo?.login
        }

        viewModel.toastFlow.launchAndCollectIn(viewLifecycleOwner) {
            toast(it)
        }

        viewModel.logoutPageFlow.launchAndCollectIn(viewLifecycleOwner) {
            logoutResponse.launch(it)
        }

        viewModel.logoutCompletedFlow.launchAndCollectIn(viewLifecycleOwner) {
            findNavController().resetNavGraph(R.navigation.nav_graph)
        }

        recyclerView = view.findViewById(R.id.repositories)
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.repositories.observe(viewLifecycleOwner) { repositories ->
            val adapter = RepositoriesListAdapter(repositories, this)
            recyclerView.adapter = adapter
        }

    }


    private fun searchRepositories(query: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val response = githubApi.searchRepositories(query)

            if (response.isSuccessful) {
                val repositories = response.body()?.items.orEmpty()
                Timber.tag("Репозитории: ").e(repositories.joinToString { it.name })
                    // Отобразить результаты поиска в виде списка элементов
                withContext(Dispatchers.Main) {

                }
            } else {
                Timber.tag("Ошибка при выполнении запроса: ").e(response.message())
            }

        }
    }

    override fun onRepositoryClick(repository: Repository) {
        TODO("Not yet implemented")
    }
}