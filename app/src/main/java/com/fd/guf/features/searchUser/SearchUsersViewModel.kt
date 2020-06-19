package com.fd.guf.features.searchUser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fd.guf.dataSource.remote.Repository
import com.fd.guf.dataSource.remote.RepositoryCallback
import com.fd.guf.models.User
import com.fd.guf.models.Users
import com.fd.guf.utils.State

class SearchUsersViewModel constructor(private val repository: Repository) : ViewModel() {

    val stateLiveData = MutableLiveData<String>()
    val usersLiveData = MutableLiveData<List<User>>()
    val errorLiveData = MutableLiveData<String>()
    var totalCount = 0

    fun searchUsers(q: String?) {
        if (q?.isEmpty() == true) {
            stateLiveData.postValue(State.SUCCESS)
            return
        }
        stateLiveData.postValue(State.LOADING)
        repository.searchUsers(q, 1, object : RepositoryCallback<Users> {
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

    fun loadMoreUsers(q: String?, page: Int?) {
        stateLiveData.postValue(State.LOAD_MORE)
        repository.searchUsers(q, page, object : RepositoryCallback<Users> {
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