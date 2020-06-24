package com.fd.guf.dataSource.remote

import com.fd.guf.models.Users
import com.fd.guf.utils.Constants
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("search/users")
    fun searchUsers(
        @Query("q") q: String?,
        @Query("page") page: Int?,
        @Query("per_page") perPage: Int? = Constants.PER_PAGE
    ): Single<Users>

}