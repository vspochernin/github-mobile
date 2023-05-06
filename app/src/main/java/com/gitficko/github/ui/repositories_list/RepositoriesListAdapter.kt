package com.gitficko.github.ui.repositories_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gitficko.github.R
import com.gitficko.github.model.Repository
import com.gitficko.github.utils.Utils

class RepositoriesListAdapter(val repositoriesList: List<Repository>) :
    RecyclerView.Adapter<RepositoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_repository, parent, false)
        return RepositoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return repositoriesList.size
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val repository = repositoriesList[position]

        holder.fullNameTextView.text = repository.fullName
        holder.privacyTextView.text = if (repository.private) "Private" else "Public"
        holder.dateInfoTextView.text = Utils.getTimeAgo(repository.updatedAt)

        if (repository.description.isNullOrEmpty()) {
            holder.descriptionLayout.visibility = View.GONE
        } else {
            holder.descriptionLayout.visibility = View.VISIBLE
            holder.descriptionTextView.text = repository.description
        }

        if (repository.language.isNullOrEmpty()) {
            holder.languageBullet.visibility = View.GONE
            holder.languageTextView.visibility = View.GONE
        } else {
            holder.languageBullet.visibility = View.VISIBLE
            holder.languageTextView.visibility = View.VISIBLE
            holder.languageTextView.text = repository.language
        }
    }
}