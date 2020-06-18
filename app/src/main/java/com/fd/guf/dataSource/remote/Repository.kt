package com.fd.guf.dataSource.remote

import com.fd.guf.R
import com.fd.guf.base.BaseApp
import com.fd.guf.models.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    private val context = BaseApp.context
    private val service = ApiService.create()

    fun searchUsers(q: String?, page: Int?, callback: RepositoryCallback<Users>) {
        service.searchUsers(q, page).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful && response.code() == 200) {
                    response.body()?.let { callback.onDataLoaded(it) }
                } else {
                    callback.onDataError(response.message())
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                callback.onDataError(context.getString(R.string.error_network))
            }
        })
    }
}