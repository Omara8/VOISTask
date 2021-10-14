package com.planatech.voistask.utils

import android.R.attr
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.planatech.voistask.R
import com.planatech.voistask.main.model.Image
import com.planatech.voistask.main.view.ImagesAdapter
import androidx.palette.graphics.Palette

import android.R.attr.bitmap
import androidx.annotation.ColorInt
import androidx.palette.graphics.Palette.PaletteAsyncListener


@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, imageUrl: String?) {
    Glide.with(imageView.context).load(imageUrl).apply(
        RequestOptions().placeholder(R.drawable.ic_placeholder_svg)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
    ).into(imageView)
}

@BindingAdapter("imageBitmap")
fun loadBitmapImage(imageView: ImageView, imageUrl: String?) {
    Glide.with(imageView.context)
        .asBitmap()
        .load(imageUrl)
        .into(object : CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                imageView.setImageBitmap(resource)
                Palette.generateAsync(resource, PaletteAsyncListener {
                    AppPreferenceUtils.setColorRGB(it?.vibrantSwatch?.rgb)
                })
            }
            override fun onLoadCleared(placeholder: Drawable?) {
                println("onLoadCleared")
            }
        })
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