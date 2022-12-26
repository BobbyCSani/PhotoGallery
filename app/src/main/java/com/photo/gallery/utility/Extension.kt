package com.photo.gallery.utility

import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun String?.ddMMMyyyy(): String {
    return try {
        val currentFormat = SimpleDateFormat(StaticValues.RESPONSE_DATE_FORMAT, Locale.getDefault())
        val currentDate = currentFormat.parse(this)
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return dateFormat.format(currentDate.time)
    } catch (e: Exception) {
        ""
    }
}

/**
 * debounce input for 1s using coroutine delay to prevent overwhelming input from user
 * and limit from free unsplash API only 50 request/hour
 * action will invoked when user already input 3 characters or more
 */
fun TextInputEditText.inputListener(scope: CoroutineScope, searchAction: (String) -> Unit) {
    var debounceJob: Job? = null
    val delayTime = 1000L

    setOnEditorActionListener { _, actionId, _ ->
        return@setOnEditorActionListener if (actionId == EditorInfo.IME_ACTION_DONE) {
            searchAction(text.toString())
            true
        } else false
    }

    this.doAfterTextChanged { editable ->
        debounceJob?.cancel()
        debounceJob = scope.launch(Dispatchers.Main) {
            delay(delayTime)
            if ((editable?.length ?: 0) >= 3) searchAction(editable.toString())
        }
    }
}

val Int.thousandFormat: String
    get() {
        try {
            if(this == 0) return "0"
            return NumberFormat.getNumberInstance().format(this)
        }catch (ex: Exception){
            return "0"
        }
    }

fun View.isVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}