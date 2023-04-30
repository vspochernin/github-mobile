package com.gitficko.github.ui.pull_requests_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gitficko.github.R
import com.gitficko.github.model.PullRequest

class PullRequestsListAdapter(var pullRequestsList: List<PullRequest>): RecyclerView.Adapter<PullRequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestViewHolder {
        val view = LayoutInflater.from(parent.context)
                                 .inflate(R.layout.layout_pull_request, parent, false)

        return PullRequestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pullRequestsList.size
    }

    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        val pullRequest = pullRequestsList[position]

        holder.pathTextView.text = holder.pathTextView.context.getString(
            R.string.path,
            "StombieIt",
            "adwjwad"
        )

        // TODO: сделать форматирование
        holder.timePassedTextView.text = pullRequest.updatedAt?:pullRequest.createdAt
        holder.titleTextView.text = pullRequest.title
    }
}