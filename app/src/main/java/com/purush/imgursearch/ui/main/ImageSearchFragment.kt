package com.purush.imgursearch.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.purush.imgursearch.R
import com.purush.imgursearch.data.source.remote.schema.Image
import com.purush.imgursearch.ui.details.ImageDetailsActivity
import com.purush.imgursearch.ui.plugins.DebounceTextWatcher
import com.purush.imgursearch.ui.plugins.DebounceTextWatcher.DebounceCompletedListener
import com.purush.imgursearch.ui.plugins.RecyclerViewPlugin
import com.purush.imgursearch.utils.getViewModelFactory
import kotlinx.android.synthetic.main.image_search_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

@Suppress("unused")
private const val TAG = "ImageSearchFragment"

class ImageSearchFragment : Fragment(), CoroutineScope, RecyclerViewPlugin.ImageClickedListener {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    // lazy initialization of view model through use of delegates and view model factory
    private val viewModel: ImageSearchViewModel by viewModels {
        getViewModelFactory()
    }

    // composition is preferred over inheritance
    private lateinit var recyclerViewPlugin: RecyclerViewPlugin

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.image_search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchField()
        setupRecyclerView(view)

        // observe changes in list of images cached by view model's live data and redraw list accordingly
        viewModel.imageList.observe(viewLifecycleOwner, {
            recyclerViewPlugin.submitList(it)
        })

        // observe changes to success flag while fetching images and reflect in UI accordingly
        viewModel.loadSuccessEvent.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled().let {
                progressBar.visibility = View.GONE
                errorTextView.visibility = View.GONE
            }
        })

        // observe changes error flag while fetching images and reflect in UI accordingly
        viewModel.loadFailureEvent.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled().let {
                Toast.makeText(
                    context,
                    getString(R.string.generic_toast_error_fetch_error),
                    Toast.LENGTH_SHORT
                ).show()
                errorTextView.visibility = View.VISIBLE
            }
            progressBar.visibility = View.GONE
        })
    }

    private fun setupSearchField() {

        val debounceTextWatcher =
            DebounceTextWatcher(this, object : DebounceCompletedListener {
                override fun onDebounceCompleted(query: String) {
                    viewModel.onQueryTextChanged(query)
                    progressBar.visibility = View.VISIBLE
                    errorTextView.visibility = View.GONE
                }
            })
        searchBox.addTextChangedListener(debounceTextWatcher.debounceTextWatcher)
    }

    private fun setupRecyclerView(view: View) {

        recyclerViewPlugin = RecyclerViewPlugin(view, this)
    }

    override fun onImageClicked(image: Image, position: Int) {

        ImageDetailsActivity.navigate(activity, image)
    }

}