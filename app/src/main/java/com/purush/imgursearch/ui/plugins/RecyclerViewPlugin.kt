package com.purush.imgursearch.ui.plugins

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.purush.imgursearch.R
import com.purush.imgursearch.data.schema.Image

class RecyclerViewPlugin(
    rootView: View,
    listener: ImageClickedListener
) {

    interface ImageClickedListener {
        fun onImageClicked(image: Image, position: Int)
    }

    fun submitList(it: List<Image>?) {
        adapter.submitList(it)
    }

    private var recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView)
    private var adapter = RecyclerViewAdapter(listener)

    init {
        recyclerView.adapter = adapter
    }

}