package com.purush.imgursearch.ui.plugins

import android.text.Editable
import android.text.TextWatcher
import com.purush.imgursearch.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DebounceTextWatcher(
    private val coroutineScope: CoroutineScope,
    private val listener: DebounceCompletedListener
) {

    interface DebounceCompletedListener {
        fun onDebounceCompleted(query: String)
    }

    val debounceTextWatcher = object : TextWatcher {
        private var query = ""

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val searchText = s.toString().trim()
            if (searchText == query)
                return

            query = searchText

            coroutineScope.launch {
                delay(Constants.DEBOUNCE_TIMEOUT)
                if (searchText != query)
                    return@launch

                listener.onDebounceCompleted(query)
            }
        }

        override fun afterTextChanged(s: Editable?) = Unit
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    }
}