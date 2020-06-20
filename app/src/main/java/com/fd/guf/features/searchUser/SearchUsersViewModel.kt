package com.fd.guf.features.searchUser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fd.guf.dataSource.remote.Repository
import com.fd.guf.dataSource.remote.RepositoryCallback
import com.fd.guf.models.User
import com.fd.guf.models.Users
import com.fd.guf.utils.Constants
import com.fd.guf.utils.State

class SearchUsersViewModel constructor(private val repository: Repository) : ViewModel() {

    val stateLiveData = MutableLiveData<String>()
    val usersLiveData = MutableLiveData<List<User>>()
    val errorLiveData = MutableLiveData<String>()
    var query = ""
    var page = Constants.START_PAGE
    var totalCount = 0

    fun searchUsers(query: String) {
        this.page = Constants.START_PAGE
        this.query = query
        if (query.isEmpty()) {
            stateLiveData.postValue(State.SUCCESS)
            return
        }
        stateLiveData.postValue(State.LOADING)
        repository.searchUsers(query, page, object : RepositoryCallback<Users> {
            override fun onDataLoaded(response: Users) {
                stateLiveData.postValue(State.SUCCESS)
                totalCount = response.totalCount
                usersLiveData.postValue(response.items)
            }

            override fun onDataError(error: String?) {
                stateLiveData.postValue(State.ERROR)
                errorLiveData.postValue(error.toString())
            }
        })
    }

    fun loadMoreUsers(query: String) {
        if (hasNexPage()) {
            page++
            stateLiveData.postValue(State.LOAD_MORE)
            repository.searchUsers(query, page, object : RepositoryCallback<Users> {
                override fun onDataLoaded(response: Users) {
                    stateLiveData.postValue(State.SUCCESS_LOAD_MORE)
                    usersLiveData.postValue(response.items)
                }

                override fun onDataError(error: String?) {
                    stateLiveData.postValue(State.ERROR_LOAD_MORE)
                    errorLiveData.postValue(error.toString())
                }
            })
        }
    }

    private fun hasNexPage(): Boolean {
        return page * Constants.PER_PAGE < totalCount
    }
}