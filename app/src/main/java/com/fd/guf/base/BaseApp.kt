package com.fd.guf.base

import android.app.Application
import android.content.Context

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        val context: Context
            get() = instance?.applicationContext as Context

        @get:Synchronized
        var instance: BaseApp? = null
            private set
    }
}