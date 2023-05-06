package com.gitficko.github.ui.repositories_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gitficko.github.R
import com.gitficko.github.model.RepositoryDto

class RepositoriesListAdapter(val repositoriesList: List<RepositoryDto>) : RecyclerView.Adapter<RepositoryViewHolder>() {
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
        holder.dateInfoTextView.text = repository.createdAt
        holder.languageTextView?.text = repository.language
    }
}