package com.fd.guf.dataSource.remote

interface RepositoryCallback<T> {
    fun onDataLoaded(response: T)
    fun onDataError(error: String?)
}