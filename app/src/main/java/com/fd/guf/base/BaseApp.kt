package com.fd.guf.base

import android.app.Application
import android.content.Context
import com.fd.guf.di.appModule
import com.fd.guf.di.networkModule
import com.fd.guf.di.repositoryModule
import com.fd.guf.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidLogger()
            androidContext(this@BaseApp)
            modules(listOf(appModule, networkModule, repositoryModule, viewModelModule))
        }
    }

    companion object {
        val context: Context
            get() = instance?.applicationContext as Context

        @get:Synchronized
        var instance: BaseApp? = null
            private set
    }
}