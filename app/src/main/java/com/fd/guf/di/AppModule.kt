package com.fd.guf.di

import com.fd.guf.base.BaseApp
import org.koin.dsl.module

val appModule = module {
    single { BaseApp }
}