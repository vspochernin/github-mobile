package com.gitficko.github.ui.pull_requests_list

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gitficko.github.R

class PullRequestViewHolder(view: View) : ViewHolder(view) {
    val pathTextView = view.findViewById<TextView>(R.id.path)
    val timePassedTextView = view.findViewById<TextView>(R.id.timePassed)
    val titleTextView = view.findViewById<TextView>(R.id.title)
}
