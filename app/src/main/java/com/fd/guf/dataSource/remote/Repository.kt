package com.fd.guf.dataSource.remote

import com.fd.guf.R
import com.fd.guf.base.BaseApp
import com.fd.guf.models.Users
import com.fd.guf.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    private val context = BaseApp.context
    private val service = ApiService.create()

    fun searchUsers(q: String?, page: Int?, callback: RepositoryCallback<Users>) {
        EspressoIdlingResource.increment()
        service.searchUsers(q, page).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful && response.code() == 200) {
                    response.body()?.let {
                        if (page == 1 && it.items.isEmpty()) {
                            callback.onDataError(context.getString(R.string.user_not_found))
                        } else {
                            callback.onDataLoaded(it)
                        }
                    }
                } else {
                    callback.onDataError(response.message())
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                callback.onDataError(context.getString(R.string.error_network))
                EspressoIdlingResource.decrement()
            }
        })
    }
}