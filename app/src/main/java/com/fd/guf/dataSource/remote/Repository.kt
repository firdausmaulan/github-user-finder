package com.fd.guf.dataSource.remote

import android.content.Context
import com.fd.guf.R
import com.fd.guf.models.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Repository constructor(private val context: Context, private val apiClient: ApiClient) {

    fun searchUsers(q: String?, page: Int?, callback: RepositoryCallback<Users>) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val result = apiClient.getService().searchUsers(q, page)
                if (page == 1 && result.items.isEmpty()) {
                    callback.onDataError(context.getString(R.string.user_not_found))
                } else {
                    callback.onDataLoaded(result)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                callback.onDataError(context.getString(R.string.error_network))
            }
        }
    }
}