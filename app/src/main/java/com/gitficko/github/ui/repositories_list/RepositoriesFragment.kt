package com.gitficko.github.ui.repositories_list

import android.os.Bundle
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gitficko.github.R
import com.gitficko.github.remote.ApiClient

class RepositoriesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_repositories, container, false)

        recyclerView = view.findViewById(R.id.repositories)
        recyclerView.layoutManager = LinearLayoutManager(container!!.context)
        recyclerView.adapter = RepositoriesListAdapter(emptyList())

        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            ApiClient.gitHubApi.getUserRepositories("Bearer ${ApiClient.token}")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    recyclerView.adapter = RepositoriesListAdapter(it)
                }, {
                    Log.e("repo_fragment_get_data_error", it.stackTraceToString())
                })
        )

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
    }
}