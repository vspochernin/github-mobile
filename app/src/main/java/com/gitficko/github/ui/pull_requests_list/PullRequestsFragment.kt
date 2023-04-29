package com.gitficko.github.ui.pull_requests_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gitficko.github.R
import com.gitficko.github.remote.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PullRequestsFragment: Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val compositeDisposible: CompositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pull_requests, container, false)

        recyclerView = view.findViewById<RecyclerView>(R.id.pullRequests)
        recyclerView.layoutManager = LinearLayoutManager(container!!.context)
        recyclerView.adapter = PullRequestsListAdapter(emptyList())

        compositeDisposible.add(ApiClient.gitHubApi.getPullRequests("polis-vk", "java-tasks")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it ->
                (recyclerView.adapter as PullRequestsListAdapter).pullRequestsList = it
            }, {
                Log.e("pull_requests_fetching", "onCreateView: ", it)
            }))

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposible.dispose()
    }
}