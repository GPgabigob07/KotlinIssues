package com.nta.githubkotlinissueapp.ui.adapter

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.nta.githubkotlinissueapp.R
import com.nta.githubkotlinissueapp.api.models.response.IssueModel
import com.nta.githubkotlinissueapp.databinding.ItemIssueBinding
import com.nta.githubkotlinissueapp.utils.*

class IssuesAdapter(
    private val openDetails: (MaterialCardView, IssueModel, Boolean) -> Unit
) : ListAdapter<IssueModel, IssuesAdapter.IssueVH>(DIFFER) {

    var isLandscape: Boolean = false
    var selectedIssue: IssueModel? = null

    var lastSelectedHolder: IssueVH? = null
        set(value) {
            if (field?.item === value?.item) {
                value?.switch()
            } else {
                field?.apply {
                    isDetailed = false
                }
                field = value?.apply {
                    switch()
                }
            }
        }

    inner class IssueVH(val binder: ItemIssueBinding) : RecyclerView.ViewHolder(binder.root) {

        val item: IssueModel?
            get() = when (adapterPosition) {
                -1 -> null
                else -> getItem(adapterPosition)
            }

        var isDetailed: Boolean = false
            set(v) {
                when (v) {
                    true -> expand()
                    false -> collapse()
                }
                field = v
            }

        init {
            binder.card.setOnClickListener {
                if (isLandscape) {
                    if (selectedIssue !== item) {
                        selectedIssue = item.also {
                            openDetails(binder.card, it!!, true)
                        }
                    } else {
                        openDetails(binder.card, item!!, false)
                    }


                    lastSelectedHolder = this
                } else {
                    openDetails(binder.card, item!!, true)
                }
            }
//            binder.detailedIssue.isVisible = isDetailed
        }

        private fun collapse() {
            TransitionManager.beginDelayedTransition(binder.card.apply {
                setCardBackgroundColor(resources!!.compatColor(
                    if (!isDarkMode) R.color.textColorNormalInv else R.color.textColorNormal, null
                ))
            }, AutoTransition())
        }

        private fun expand() {
            TransitionManager.beginDelayedTransition(binder.card.apply {
                setCardBackgroundColor(resources!!.compatColor(
                    R.color.colorPrimary, resources.newTheme()
                ))
            }, AutoTransition())
        }

        fun switch() {
            isDetailed = !isDetailed
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        IssueVH(ItemIssueBinding.inflate(parent.inflater, parent, false))

    override fun onBindViewHolder(holder: IssueVH, position: Int) {
        with(holder.binder) {
            issue = getItem(position)
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


