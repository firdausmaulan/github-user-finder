package com.fd.guf.custom.action

import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.FragmentActivity
import java.util.*

class DelayedTextWatcher(private val context: FragmentActivity?) : TextWatcher {
    private var timer: Timer = Timer()
    private var listener: Listener? = null

    interface Listener {
        fun onTextChanged(data: String)
    }

    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    override fun afterTextChanged(editable: Editable?) {
        timer.cancel()
        timer = Timer()
        timer.schedule(
            object : TimerTask() {
                override fun run() {
                    context?.runOnUiThread {
                        listener?.onTextChanged(
                            editable.toString()
                        )
                    }
                }
            },
            1000
        )
    }

    override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

}