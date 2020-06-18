package com.fd.guf.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager


object KeyboardUtil {
    fun hide(view: View) {
        try {
            val context = view.context
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}