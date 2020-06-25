package com.fd.guf.di

import com.fd.guf.dataSource.remote.ApiClient
import org.koin.dsl.module

val networkModule = module {
    single { ApiClient(get()) }
}