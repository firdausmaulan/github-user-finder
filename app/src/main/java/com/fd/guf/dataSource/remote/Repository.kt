package com.fd.guf.dataSource.remote

import android.util.Log
import com.fd.guf.R
import com.fd.guf.base.BaseApp
import com.fd.guf.models.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Repository {

    private val context = BaseApp.context
    private val service = ApiService.create()

    fun searchUsers(q: String?, page: Int?, callback: RepositoryCallback<Users>) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val result = service.searchUsers(q, page)
                if (page == 1 && result.items.isEmpty()) {
                    callback.onDataError(context.getString(R.string.user_not_found))
                } else {
                    callback.onDataLoaded(result)
                }
            } catch (e : Exception) {
                Log.e("searchUsers", e.localizedMessage)
                e.printStackTrace()
                callback.onDataError(context.getString(R.string.error_network))
            }
        }
    }
}