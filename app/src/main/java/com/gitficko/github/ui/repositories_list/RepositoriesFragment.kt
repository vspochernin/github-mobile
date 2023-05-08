package com.gitficko.github.ui.repositories_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gitficko.github.R
import com.gitficko.github.model.Repository
import com.gitficko.github.remote.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class RepositoriesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)

        val view = inflater.inflate(R.layout.fragment_repositories, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        // Инициализация Toolbar.
        toolbar.title = "Repositories"
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


        recyclerView = view.findViewById(R.id.repositories)
        recyclerView.layoutManager = LinearLayoutManager(container!!.context)
        recyclerView.adapter = RepositoriesListAdapter(emptyList())

        ApiClient.gitHubApi.getUserRepositories("Bearer ${ApiClient.token}").enqueue(object :
            Callback<List<Repository>> {
            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                recyclerView.adapter = RepositoriesListAdapter(response.body()!!)
            }

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                Timber.tag("repo_fragment_get_data_error").e(t.stackTraceToString())
            }
        })

        return view
    }
}