package com.fd.guf.di

import com.fd.guf.features.searchUser.SearchUsersActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, RepositoryModule::class])
interface AppComponent {
    fun inject(searchUsersActivity: SearchUsersActivity)
}