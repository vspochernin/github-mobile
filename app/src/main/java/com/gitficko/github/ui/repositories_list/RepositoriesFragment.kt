package com.gitficko.github.ui.repositories_list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_repositories, container, false)

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