package com.gitficko.github.ui.organizations_list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gitficko.github.R

class OrganizationsViewHolder(view: View) : ViewHolder(view) {
    val loginTextView = view.findViewById<TextView>(R.id.login)
    val descriptionTextView = view.findViewById<TextView>(R.id.description)
    val avatarImageView = view.findViewById<ImageView>(R.id.avatar)
}
