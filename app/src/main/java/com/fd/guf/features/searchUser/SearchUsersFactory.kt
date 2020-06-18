package com.fd.guf.features.searchUser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fd.guf.dataSource.remote.Repository

class SearchUsersFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchUsersViewModel(repository) as T
    }
}