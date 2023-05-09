package com.gitficko.github.ui.files_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gitficko.github.R
import com.gitficko.github.model.ContentDto

class RepositoryContentsAdapter(private val contentClickListener: ContentClickListener) :
    ListAdapter<ContentDto, RepositoryContentsAdapter.ContentViewHolder>(ContentDiffCallback()) {

    interface ContentClickListener {
        fun onContentClick(contentDto: ContentDto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.repository_content_item, parent, false)
        return ContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val contentNameTextView: TextView = itemView.findViewById(R.id.contentNameTextView)

        fun bind(contentDto: ContentDto) {
            contentNameTextView.text = contentDto.name
            itemView.setOnClickListener {
                contentClickListener.onContentClick(contentDto)
            }
        }
    }

    class ContentDiffCallback : DiffUtil.ItemCallback<ContentDto>() {
        override fun areItemsTheSame(oldItem: ContentDto, newItem: ContentDto): Boolean {
            return oldItem.sha == newItem.sha
        }

        override fun areContentsTheSame(oldItem: ContentDto, newItem: ContentDto): Boolean {
            return oldItem == newItem
        }
    }
}