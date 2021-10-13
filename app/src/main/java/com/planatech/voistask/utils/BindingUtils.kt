package com.planatech.voistask.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.planatech.voistask.R
import com.planatech.voistask.main.model.Image
import com.planatech.voistask.main.view.ImagesAdapter

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, imageUrl: String?) {
    Glide.with(imageView.context).load(imageUrl).apply(
        RequestOptions().placeholder(R.drawable.ic_placeholder_svg)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
    ).into(imageView)
}

@BindingAdapter("text")
fun loadText(textView: MaterialTextView, name: String?) {
    textView.text = name
}

@BindingAdapter("setAdapter")
fun RecyclerView.bindRecyclerViewAdapter(adapter: PagingDataAdapter<Image, ImagesAdapter.ImagesViewHolder>?) {
    this.run {
        this.setHasFixedSize(true)
        this.adapter = adapter
    }
}