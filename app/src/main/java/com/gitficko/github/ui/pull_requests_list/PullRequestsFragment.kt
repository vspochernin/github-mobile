package com.gitficko.github.ui.pull_requests_list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class PullRequestsFragment: Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pull_requests, container, false)

        recyclerView = view.findViewById<RecyclerView>(R.id.pullRequests)
        recyclerView.layoutManager = LinearLayoutManager(container!!.context)
        recyclerView.adapter = PullRequestsListAdapter(emptyList())

        CoroutineScope(Dispatchers.IO).launch {
            val pullRequests = CachedClient.getPullRequestsOf(
                context!!.getSharedPreferences(SharedPreferencesKey.CURRENT_USER.value, Context.MODE_PRIVATE)
                         .getString(CurrentUserPreferencesKey.LOGIN.value, null)!!
            )
            Log.i("some_data", pullRequests.toString())
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