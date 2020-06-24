package com.fd.guf.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(
    @get:Provides
    @get:Singleton val context: Context
)