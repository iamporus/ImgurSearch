package com.purush.imgursearch.ui.plugins

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.purush.imgursearch.R
import com.purush.imgursearch.data.source.remote.schema.Image

class RecyclerViewAdapter(
    private val listener: RecyclerViewPlugin.ImageClickedListener
) :
    ListAdapter<Image, RecyclerViewAdapter.BaseViewHolder>(ImageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.grid_list_item_layout, parent, false
            ), listener
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)
    }

    class BaseViewHolder(
        private val rowItemView: View,
        private val listener: RecyclerViewPlugin.ImageClickedListener
    ) :
        RecyclerView.ViewHolder(rowItemView) {

        fun bind(item: Image, position: Int) {

            val view: ImageView = rowItemView.findViewById(R.id.imageView)
            view.setOnClickListener {
                listener.onImageClicked(item, position)
            }
            Glide.with(rowItemView.context).load(item.link)
                .centerCrop()
                .apply(
                    RequestOptions()
                        .override(100, 100)
                )
                .placeholder(ColorDrawable(Color.GRAY))
                .error(ColorDrawable(Color.RED))
                .into(view)
        }

    }

    /**
     * Callback for calculating the diff between two non-null items in a list.
     *
     * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
     * list that's been passed to `submitList`.
     */
    class ImageDiffCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }
}