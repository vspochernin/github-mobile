package com.gitficko.github.ui.pull_requests_list

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gitficko.github.R
import com.gitficko.github.model.CurrentUserPreferencesKey
import com.gitficko.github.model.SharedPreferencesKey
import com.gitficko.github.remote.CachedClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class PullRequestsFragment: Fragment() {
    private lateinit var recyclerView: RecyclerView

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pull_requests, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        // Инициализация Toolbar.
        toolbar.title = "Pull Requests"
        toolbar.setNavigationIcon(R.drawable.arrow_back)
        toolbar.inflateMenu(R.menu.menu_search)

        // Обработка нажатия на кнопку "назад".
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        // Настройка SearchView
        val searchItem = toolbar.menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search pull requests..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // TODO: Реализовать функцию поиска при нажатии на кнопку "Enter" на клавиатуре.
                Timber.tag("Ищем ").e(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // TODO: Реализовать функцию поиска при изменении текста в поле поиска.
                Timber.tag("Текст поска изменился на ").e(newText)
                return false
            }
        })

        recyclerView = view.findViewById(R.id.pullRequests)
        recyclerView.layoutManager = LinearLayoutManager(container!!.context)
        recyclerView.adapter = PullRequestsListAdapter(emptyList())

        CoroutineScope(Dispatchers.IO).launch {
            val pullRequests = CachedClient.getPullRequestsOf(
                context!!.getSharedPreferences(SharedPreferencesKey.CURRENT_USER.value, Context.MODE_PRIVATE)
                         .getString(CurrentUserPreferencesKey.LOGIN.value, null)!!
            )
            requireActivity().runOnUiThread {
                recyclerView.adapter = PullRequestsListAdapter(pullRequests)
                recyclerView.adapter!!.notifyDataSetChanged()
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}