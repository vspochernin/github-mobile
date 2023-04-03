package company.vk.polis.github_mobile.ui.repositories_list

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import company.vk.polis.github_mobile.R

class RepositoryViewHolder(view: View) : ViewHolder(view) {
    val fullNameTextView = view.findViewById<TextView>(R.id.fullName)
    val privacyTextView = view.findViewById<TextView>(R.id.privacy)
    val languageTextView = view.findViewById<TextView>(R.id.language)
    val dateInfoTextView = view.findViewById<TextView>(R.id.dateInfo)
}