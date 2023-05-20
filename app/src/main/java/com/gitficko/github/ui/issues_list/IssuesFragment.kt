package com.gitficko.github.ui.issues_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gitficko.github.R
import com.gitficko.github.model.Issue
import com.gitficko.github.remote.ApiClient
import kotlinx.coroutines.launch

class IssuesFragment: Fragment() {
    private var recyclerView: RecyclerView? = null
    private var viewModel: IssuesViewModel? = null
    private var currentIssuesList = emptyList<Issue>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_issues, container, false)

        recyclerView = view.findViewById<RecyclerView>(R.id.issues)
        recyclerView!!.layoutManager = LinearLayoutManager(container!!.context)
        recyclerView!!.adapter = IssuesListAdapter(currentIssuesList)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        // Инициализация Toolbar.
        toolbar.title = "Issues"
        toolbar.setNavigationIcon(R.drawable.arrow_back)
        toolbar.inflateMenu(R.menu.menu_search)

        // Обработка нажатия на кнопку "назад".
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        // Настройка SearchView
        val searchItem = toolbar.menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search issues..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel!!.updateQuery(query?:"")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        searchView.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                viewModel!!.updateQuery("")
                return false
            }
        })

        viewModel = ViewModelProvider(this).get(IssuesViewModel::class.java)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel!!.suitableIssues.collect { issues ->
                    (recyclerView!!.adapter as IssuesListAdapter).submitList(issues)
                }
            }
        }

        viewModel!!.loadIssuesByToken(ApiClient.token!!)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView = null
        viewModel = null
    }
}