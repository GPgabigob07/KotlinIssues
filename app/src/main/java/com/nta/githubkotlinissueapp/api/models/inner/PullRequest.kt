package com.nta.githubkotlinissueapp.api.models.inner

import com.google.gson.annotations.SerializedName

data class PullRequest(
    val url: String,

    @SerializedName("html_url")
    val htmlURL: String,

    @SerializedName("diff_url")
    val diffURL: String,

    @SerializedName("patch_url")
    val patchURL: String
)