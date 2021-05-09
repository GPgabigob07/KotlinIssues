package com.nta.githubkotlinissueapp.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.nta.githubkotlinissueapp.api.models.response.IssueModel
import com.nta.githubkotlinissueapp.databinding.ItemIssueBinding
import com.nta.githubkotlinissueapp.utils.inflater

class IssuesAdapter(
    private val openDetails: (MaterialCardView, IssueModel) -> Unit
) : ListAdapter<IssueModel, IssuesAdapter.IssueVH>(DIFFER) {

    inner class IssueVH(val binder: ItemIssueBinding) : RecyclerView.ViewHolder(binder.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        IssueVH(ItemIssueBinding.inflate(parent.inflater, parent, false))

    override fun onBindViewHolder(holder: IssueVH, position: Int) {
        with(holder.binder) {
            issue = getItem(position)
            root.setOnClickListener {
                openDetails(card, getItem(position))
            }

            status.setCardBackgroundColor(getItem(position).getColor(root.context))
        }
    }

    object DIFFER : DiffUtil.ItemCallback<IssueModel>() {
        override fun areItemsTheSame(oldItem: IssueModel, newItem: IssueModel): Boolean {
            return oldItem.id == newItem.id && oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: IssueModel, newItem: IssueModel): Boolean {
            return false
        }

    }
}


