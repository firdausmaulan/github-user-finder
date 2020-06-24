package com.fd.guf.dataSource.remote

import android.content.Context
import com.fd.guf.R
import com.fd.guf.base.BaseApp
import com.fd.guf.models.Users
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class Repository constructor(private val context: Context, private val service: ApiService) {

    private val compositeDisposable = CompositeDisposable()

    fun searchUsers(q: String?, page: Int?, callback: RepositoryCallback<Users>) {
        val disposable = service.searchUsers(q, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { users ->
                    if (page == 1 && users.items.isEmpty()) {
                        callback.onDataError(context.getString(R.string.user_not_found))
                    } else {
                        callback.onDataLoaded(users)
                    }
                },
                {
                    callback.onDataError(context.getString(R.string.error_network))
                }
            )
        compositeDisposable.add(disposable)
    }

    fun dispose() {
        compositeDisposable.dispose()
    }
}