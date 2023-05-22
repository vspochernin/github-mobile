package com.gitficko.github.ui.organizations_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gitficko.github.R
import com.gitficko.github.remote.ApiClient
import kotlinx.coroutines.launch
import timber.log.Timber

class OrganizationsFragment: Fragment() {
    private val viewModel: OrganizationsListViewModel by viewModels()
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pull_requests, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        // Инициализация Toolbar.
        toolbar.title = getString(R.string.title_organizations)
        toolbar.setNavigationIcon(R.drawable.arrow_back)
        toolbar.inflateMenu(R.menu.menu_search)

        // Обработка нажатия на кнопку "назад".
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        // Настройка SearchView
        val searchItem = toolbar.menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search organizations..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Timber.tag("organizations_query_text").i(query)
                viewModel.updateQuery(query?:"")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // TODO: Реализовать функцию поиска при изменении текста в поле поиска.
                Timber.tag("organizations_text_change").i(newText)
                return false
            }
        })

        searchView.setOnCloseListener {
            Timber.tag("organizations_query").i("close")
            viewModel.updateQuery("")
            false
        }

        recyclerView = view.findViewById(R.id.pullRequests)
        recyclerView!!.layoutManager = LinearLayoutManager(container!!.context)
        recyclerView!!.adapter = OrganizationsListAdapter(emptyList())

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.suitableList.collect { organizations ->
                    (recyclerView!!.adapter as OrganizationsListAdapter).submitList(organizations)
                }
            }
        }

        viewModel.loadOrganizationsByToken(ApiClient.token!!)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView = null
    }
}