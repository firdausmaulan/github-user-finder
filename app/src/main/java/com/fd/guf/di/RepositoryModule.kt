package com.fd.guf.di

import android.content.Context
import com.fd.guf.dataSource.remote.ApiService
import com.fd.guf.dataSource.remote.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [AppModule::class, NetworkModule::class])
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(context: Context, service: ApiService): Repository {
        return Repository(context, service)
    }

}