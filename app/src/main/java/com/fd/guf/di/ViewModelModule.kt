package com.fd.guf.di

import com.fd.guf.features.searchUser.SearchUsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SearchUsersViewModel(get()) }
}