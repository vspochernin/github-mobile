package com.gitficko.github.ui.pull_requests_list

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gitficko.github.R
import com.gitficko.github.model.Issue
import com.gitficko.github.model.ListComparatorAdapter
import com.gitficko.github.model.PullRequest
import com.gitficko.github.utils.formatSecondsToTimePassed
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class PullRequestsListAdapter(var pullRequestsList: List<PullRequest>): RecyclerView.Adapter<PullRequestViewHolder>(),
                                                                        ListComparatorAdapter<PullRequest> {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestViewHolder {
        val view = LayoutInflater.from(parent.context)
                                 .inflate(R.layout.layout_pull_request, parent, false)

        return PullRequestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pullRequestsList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        val pullRequest = pullRequestsList[position]

        holder.numberTextView.text = holder.numberTextView.context.getString(
            R.string.number,
            pullRequest.number.toString()
        )

        val differenceMillis = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) -
            LocalDateTime.parse(pullRequest.updatedAt?:pullRequest.createdAt, formatter)
                .toEpochSecond(ZoneOffset.UTC)
        holder.timePassedTextView.text = formatSecondsToTimePassed(differenceMillis)
        holder.titleTextView.text = pullRequest.title
    }

    override fun submitList(newPullrequestsList: List<PullRequest>) {
        val diffResult = DiffUtil.calculateDiff(object: DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return pullRequestsList.size
            }

            override fun getNewListSize(): Int {
                return newPullrequestsList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return pullRequestsList[oldItemPosition].id == newPullrequestsList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return pullRequestsList[oldItemPosition] == newPullrequestsList[newItemPosition]
            }
        })
        pullRequestsList = newPullrequestsList
        diffResult.dispatchUpdatesTo(this)
    }
}