package com.blueray.bashitistores.helpers


import android.view.View
import android.widget.EditText
import androidx.core.text.HtmlCompat

object ViewUtils {

    fun View.hide() {
        visibility = View.GONE
    }

    fun View.show() {
        visibility = View.VISIBLE
    }

    fun View.inVisible() {
        visibility = View.INVISIBLE
    }

    fun EditText.isInputEmpty(): Boolean {
        return text.toString().trim().isEmpty()
    }

    fun String.toHTML(): String {
        return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    }

}