package com.fd.guf.base

import android.R.id
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.R
import com.google.android.material.snackbar.Snackbar


open class BaseActivity : AppCompatActivity() {

    fun showErrorMessage(message: String) {
        val view: View = window.decorView.findViewById(id.content)
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        val sbView = snackBar.view
        sbView.setBackgroundColor(Color.RED)
        val textView: TextView = sbView.findViewById(R.id.snackbar_text)
        textView.setTextColor(Color.WHITE)
        snackBar.show()
    }
}