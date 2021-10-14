package com.planatech.voistask.main.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.planatech.voistask.BR
import com.planatech.voistask.databinding.ImageItemBinding
import com.planatech.voistask.main.model.Image
import java.util.*

class ImagesAdapter(private val itemClickCallback: (item: Image) -> Unit) :
    PagingDataAdapter<Image, ImagesAdapter.ImagesViewHolder>(diffCallBack) {
    class ImagesViewHolder(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val item = when (holder.itemViewType) {
            1 -> {
                getItem(position)
            }
            else -> {
                Image(Date().time.toInt(), "AD HERE", 0, 0, "", "")
            }
        }

        if (item != null) {
            holder.binding.setVariable(BR.image, item)
            holder.itemView.setOnClickListener {
                itemClickCallback(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ImageItemBinding.inflate(layoutInflater, parent, false)
        return ImagesViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position != 0 && position % 5 == 0) {
            0
        } else 1
    }

    companion object {
        private val diffCallBack = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean =
                oldItem == newItem
        }
    }
}