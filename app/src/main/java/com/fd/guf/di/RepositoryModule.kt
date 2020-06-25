package com.fd.guf.di

import com.fd.guf.dataSource.remote.Repository
import org.koin.dsl.module

val repositoryModule = module {
    single { Repository(get(), get()) }
}