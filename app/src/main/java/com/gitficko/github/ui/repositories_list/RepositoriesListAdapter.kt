package com.gitficko.github.ui.repositories_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gitficko.github.R
import com.gitficko.github.model.ListComparatorAdapter
import com.gitficko.github.model.Repository
import com.gitficko.github.utils.Utils

class RepositoriesListAdapter(
    private var repositoriesList: List<Repository>,
    private val clickListener: RepositoryClickListener
) : RecyclerView.Adapter<RepositoryViewHolder>(),
    ListComparatorAdapter<Repository> {
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
        holder.dateInfoTextView.text = Utils.getTimeAgo(repository.updatedAt?:repository.createdAt)

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

        holder.itemView.setOnClickListener {
            clickListener.onRepositoryClick(repository)
        }
    }

    override fun submitList(newRepositoriesList: List<Repository>) {
        val diffResult = DiffUtil.calculateDiff(object: DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return repositoriesList.size
            }

            override fun getNewListSize(): Int {
                return newRepositoriesList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return repositoriesList[oldItemPosition].id == newRepositoriesList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return repositoriesList[oldItemPosition] == newRepositoriesList[newItemPosition]
            }
        })
        repositoriesList = newRepositoriesList
        diffResult.dispatchUpdatesTo(this)
    }
}