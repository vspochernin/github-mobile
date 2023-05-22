package com.gitficko.github.ui.repositories_list

import RepositoriesSourceType
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gitficko.github.R
import com.gitficko.github.model.Repository
import com.gitficko.github.remote.ApiClient
import com.gitficko.github.utils.Utils
import kotlinx.coroutines.launch
import timber.log.Timber

class RepositoriesFragment : Fragment(), RepositoryClickListener {
    private val viewModel: RepositoriesListViewModel by viewModels()
    private var recyclerView: RecyclerView? = null
    private lateinit var source: RepositoriesSourceType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        source = arguments?.getString("source")?.let { RepositoriesSourceType.valueOf(it) }
            ?: RepositoriesSourceType.DEFAULT
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)

        val view = inflater.inflate(R.layout.fragment_repositories, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        // Инициализация Toolbar.
        toolbar.title = getString(R.string.repositories_sign)
        toolbar.setNavigationIcon(R.drawable.arrow_back)
        toolbar.inflateMenu(R.menu.menu_search)

        // Обработка нажатия на кнопку "назад".
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        // Настройка SearchView
        val searchItem = toolbar.menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search repositories..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Timber.tag("repositories_query_text").i(query)
                viewModel.updateQuery(query?:"")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Timber.tag("repositories_text_change").i(newText)
                return true
            }
        })

        searchView.setOnCloseListener {
            Timber.tag("repositories_close")
            viewModel.updateQuery("")
            true
        }

        recyclerView = view.findViewById(R.id.repositories)
        recyclerView!!.layoutManager = LinearLayoutManager(container!!.context)
        recyclerView!!.adapter = RepositoriesListAdapter(emptyList(), this)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.list.collect { repositories ->
                    (recyclerView!!.adapter as RepositoriesListAdapter).submitList(repositories)
                }
            }
        }

        viewModel.loadRepositoriesByToken(ApiClient.token!!, source)

        return view
    }

    override fun onRepositoryClick(repository: Repository) {
        val action = RepositoriesFragmentDirections
            .actionNavigationRepositoriesToRepositoryFragment(
                repository.ownerLogin,
                repository.name,
                repository.description.orEmpty()
            )
        findNavController().navigate(action, Utils.navOptionsToRight)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView = null
    }
}