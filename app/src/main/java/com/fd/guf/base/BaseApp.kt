package com.fd.guf.base

import android.app.Application
import android.content.Context
import com.fd.guf.di.*

class BaseApp : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .repositoryModule(RepositoryModule())
            .build()
    }

    companion object {
        val context: Context
            get() = instance?.applicationContext as Context

        @get:Synchronized
        var instance: BaseApp? = null
            private set
    }

    fun getComponent(): AppComponent {
        return appComponent
    }
}