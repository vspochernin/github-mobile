package com.gitficko.github.ui.repositories_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gitficko.github.R
import com.gitficko.github.model.Repository
import com.gitficko.github.model.RepositoryDto
import com.gitficko.github.remote.ApiClient
import com.gitficko.github.remote.Networking
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.stream.Collectors

class RepositoriesFragment : Fragment(), RepositoryClickListener {
    private lateinit var recyclerView: RecyclerView

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
        recyclerView.adapter = RepositoriesListAdapter(emptyList(), this)

        CoroutineScope(Dispatchers.IO).launch {
            val repositories = Networking.githubApi
                .getUserRepositories("Bearer ${ApiClient.token}")
                .stream()
                .map(RepositoryDto::toEntity)
                .collect(Collectors.toList())
            requireActivity().runOnUiThread {
                recyclerView.adapter =
                    RepositoriesListAdapter(repositories, this@RepositoriesFragment)
                recyclerView.adapter!!.notifyDataSetChanged()
            }
        }
        return view
    }

    override fun onRepositoryClick(repository: Repository) {
        val action = RepositoriesFragmentDirections
            .actionNavigationRepositoriesToRepositoryFragment(
                repository.ownerLogin,
                repository.name,
                repository.description.orEmpty()
            )
        findNavController().navigate(action)
    }
}