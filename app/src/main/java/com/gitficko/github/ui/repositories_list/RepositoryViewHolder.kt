package com.gitficko.github.ui.repositories_list

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gitficko.github.R

class RepositoryViewHolder(view: View) : ViewHolder(view) {
    val fullNameTextView = view.findViewById<TextView>(R.id.fullName)
    val privacyTextView = view.findViewById<TextView>(R.id.privacy)
    val languageTextView = view.findViewById<TextView>(R.id.language)
    val dateInfoTextView = view.findViewById<TextView>(R.id.dateInfo)
    val descriptionTextView = view.findViewById<TextView>(R.id.description)

    val descriptionLayout = view.findViewById<LinearLayout>(R.id.language_id)
    val languageBullet = view.findViewById<ImageView>(R.id.repo_bullet_id)
}