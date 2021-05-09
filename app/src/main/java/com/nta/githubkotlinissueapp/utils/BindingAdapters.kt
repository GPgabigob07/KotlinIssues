package com.nta.githubkotlinissueapp.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.nta.githubkotlinissueapp.R

@BindingAdapter("loadUrl")
fun ImageView.loadUrl(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.ic_kotlin_small)
        .into(this)
}