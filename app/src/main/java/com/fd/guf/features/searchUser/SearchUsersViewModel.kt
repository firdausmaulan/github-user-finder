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

    fun searchUsers(q: String?, page: Int?) {
        if (page == 1) {
            stateLiveData.postValue(State.LOADING)
        } else {
            stateLiveData.postValue(State.LOAD_MORE)
        }
        repository.searchUsers(q, page, object : RepositoryCallback<Users> {
            override fun onDataLoaded(response: Users) {
                if (page == 1) {
                    stateLiveData.postValue(State.SUCCESS)
                } else {
                    stateLiveData.postValue(State.SUCCESS_LOAD_MORE)
                }
                usersLiveData.postValue(response.items)
            }

            override fun onDataError(error: String?) {
                if (page == 1) {
                    stateLiveData.postValue(State.ERROR)
                } else {
                    stateLiveData.postValue(State.ERROR_LOAD_MORE)
                }
                errorLiveData.postValue(error.toString())
            }
        })
    }
}