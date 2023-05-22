package com.gitficko.github.ui.organizations_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gitficko.github.R
import com.gitficko.github.model.ListComparatorAdapter
import com.gitficko.github.model.Organization
import com.squareup.picasso.Picasso

class OrganizationsListAdapter(var organizationsList: List<Organization>): RecyclerView.Adapter<OrganizationsViewHolder>(),
                                                                           ListComparatorAdapter<Organization> {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganizationsViewHolder {
        val view = LayoutInflater.from(parent.context)
                                 .inflate(R.layout.layout_organization, parent, false)
        return OrganizationsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return organizationsList.size
    }

    override fun onBindViewHolder(holder: OrganizationsViewHolder, position: Int) {
        val organization = organizationsList[position]

        holder.loginTextView.text = organization.login
        holder.descriptionTextView.text = organization.description
        Picasso.get().load(organization.avatarUrl).into(holder.avatarImageView)
    }

    override fun submitList(newOrganizationsList: List<Organization>) {
        val diffResult = DiffUtil.calculateDiff(object: DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return organizationsList.size
            }

            override fun getNewListSize(): Int {
                return newOrganizationsList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return organizationsList[oldItemPosition].id == newOrganizationsList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return organizationsList[oldItemPosition] == newOrganizationsList[newItemPosition]
            }
        })
        organizationsList = newOrganizationsList
        diffResult.dispatchUpdatesTo(this)
    }
}