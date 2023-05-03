package com.gitficko.github.ui.repositories_list

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gitficko.github.R

class RepositoryViewHolder(view: View) : ViewHolder(view) {
    val fullNameTextView: TextView = view.findViewById(R.id.fullName)
    val privacyTextView: TextView = view.findViewById(R.id.privacy)
    val languageTextView: TextView? = view.findViewById(R.id.language)
    val dateInfoTextView: TextView = view.findViewById(R.id.dateInfo)
}