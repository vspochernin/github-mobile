package com.gitficko.github.ui.pull_requests_list

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gitficko.github.R

class PullRequestViewHolder(view: View) : ViewHolder(view) {
    val numberTextView = view.findViewById<TextView>(R.id.number)
    val timePassedTextView = view.findViewById<TextView>(R.id.timePassed)
    val titleTextView = view.findViewById<TextView>(R.id.title)
}
