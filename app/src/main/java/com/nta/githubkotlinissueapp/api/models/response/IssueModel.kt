package com.nta.githubkotlinissueapp.api.models.response

import android.content.Context
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.google.gson.annotations.SerializedName
import com.nta.githubkotlinissueapp.R
import com.nta.githubkotlinissueapp.api.models.inner.GitUser
import com.nta.githubkotlinissueapp.api.models.inner.PullRequest
import java.util.*

data class IssueModel(
    val url: String?,

    @SerializedName("repository_url")
    val repositoryURL: String?,

    @SerializedName("labels_url")
    val labelsURL: String?,

    @SerializedName("comments_url")
    val commentsURL: String?,

    @SerializedName("events_url")
    val eventsURL: String?,

    @SerializedName("html_url")
    val htmlURL: String?,

    val id: Long,

    @SerializedName("node_id")
    val nodeID: String?,

    val number: Long,
    val title: String?,
    val user: GitUser,
    val labels: List<Any?>,
    val state: String?,
    val locked: Boolean,
    val assignee: Any? = null,
    val assignees: List<Any?>,
    val milestone: Any? = null,
    val comments: Long,

    @SerializedName("created_at")
    val createdAt: Date?,

    @SerializedName("updated_at")
    val updatedAt: Date?,

    @SerializedName("closed_at")
    val closedAt: Date? = null,

    @SerializedName("author_association")
    val authorAssociation: String?,

    @SerializedName("active_lock_reason")
    val activeLockReason: Any? = null,

    @SerializedName("pull_request")
    val pullRequest: PullRequest,

    val body: String?,

    @SerializedName("performed_via_github_app")
    val performedViaGithubApp: Any? = null
) {
    val displayTitle: String?
        get() = title?.capitalize()

    val displayState: String?
        get() = state?.capitalize()

    @ColorInt
    fun getColor(c: Context): Int {
        return ContextCompat.getColor(
            c,
            if (state == "open") R.color.openGreen else R.color.closeRed
        )
    }
}

