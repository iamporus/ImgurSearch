package com.purush.imgursearch.ui.main.plugins

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.purush.imgursearch.R
import com.purush.imgursearch.data.schema.Image

class RecyclerViewPlugin(
    rootView: View
) {
    fun submitList(it: List<Image>?) {
        adapter.submitList(it)
    }

    private var recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView)
    private var adapter = RecyclerViewAdapter()

    init {
        recyclerView.adapter = adapter
    }

}