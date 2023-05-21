package com.gitficko.github.ui.issues_list

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gitficko.github.R
import com.gitficko.github.model.Issue
import com.gitficko.github.model.ListComparatorAdapter
import com.gitficko.github.utils.formatSecondsToTimePassed
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class IssuesListAdapter(var issuesList: List<Issue>): RecyclerView.Adapter<IssueViewHolder>(),
                                                      ListComparatorAdapter<Issue> {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val view = LayoutInflater.from(parent.context)
                                 .inflate(R.layout.layout_issue, parent, false)

        return IssueViewHolder(view)
    }

    override fun getItemCount(): Int {
        return issuesList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        val issue = issuesList[position]

        holder.pathTextView.text = holder.pathTextView.context.getString(
            R.string.path,
            issue.ownerLogin,
            issue.repositoryName,
            issue.number
        )

        val differenceMillis = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) -
            LocalDateTime.parse(issue.updatedAt?:issue.createdAt, formatter)
                .toEpochSecond(ZoneOffset.UTC)
        holder.timePassedTextView.text = formatSecondsToTimePassed(differenceMillis)
        holder.titleTextView.text = issue.title
    }

    override fun submitList(newIssuesList: List<Issue>) {
        val diffResult = DiffUtil.calculateDiff(object: DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return issuesList.size
            }

            override fun getNewListSize(): Int {
                return newIssuesList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return issuesList[oldItemPosition].id == newIssuesList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return issuesList[oldItemPosition] == newIssuesList[newItemPosition]
            }
        })
        issuesList = newIssuesList
        diffResult.dispatchUpdatesTo(this)
    }
}