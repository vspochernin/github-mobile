package com.gitficko.github.ui.pull_requests_list

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.gitficko.github.R
import com.gitficko.github.model.PullRequest
import com.gitficko.github.utils.formatMillisToTimePassed
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class PullRequestsListAdapter(var pullRequestsList: List<PullRequest>): RecyclerView.Adapter<PullRequestViewHolder>() {
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

        // TODO: сделать форматирование
        val differenceMillis = LocalDateTime.parse(pullRequest.updatedAt?:pullRequest.createdAt, formatter).toEpochSecond(ZoneOffset.UTC)
                                            - LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)

        holder.timePassedTextView.text = formatMillisToTimePassed(differenceMillis)
        holder.titleTextView.text = pullRequest.title
    }
}