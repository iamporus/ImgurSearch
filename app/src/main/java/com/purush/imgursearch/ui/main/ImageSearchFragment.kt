package com.purush.imgursearch.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.purush.imgursearch.R
import com.purush.imgursearch.utils.getViewModelFactory

@Suppress("unused")
private const val TAG = "ImageSearchFragment"

@Suppress("unused")
class ImageSearchFragment : Fragment() {

    // lazy initialization of view model through use of delegates and view model factory
    private val viewModel: ImageSearchViewModel by viewModels {
        getViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.image_search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchField(view)
        setupRecyclerView(view)

        //TODO: observe for data changes on view model
    }

    @Suppress("UNUSED_PARAMETER")
    private fun setupSearchField(view: View) {
        //TODO:
    }

    @Suppress("UNUSED_PARAMETER")
    private fun setupRecyclerView(view: View) {

        //TODO:
    }

}