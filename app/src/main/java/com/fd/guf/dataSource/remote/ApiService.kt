package com.fd.guf.dataSource.remote

import com.fd.guf.BuildConfig
import com.fd.guf.base.BaseApp
import com.fd.guf.models.Users
import com.fd.guf.utils.Constants
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object Factory {
        fun create(): ApiService {
            val retrofit = retrofit2.Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)

            val client = OkHttpClient.Builder()

            if (BuildConfig.DEBUG) client.addInterceptor(ChuckInterceptor(BaseApp.context))

            client.addInterceptor { chain ->
                val request = chain.request()
                val newRequest = request.newBuilder()
                    .addHeader("Accept", "application/json")
                    .build()
                chain.proceed(newRequest)
            }

            return retrofit.client(client.build()).build().create(ApiService::class.java)
        }
    }


    @GET("search/users")
    fun searchUsers(
        @Query("q") q: String?,
        @Query("page") page: Int?,
        @Query("per_page") perPage : Int? = Constants.PER_PAGE
    ): Call<Users>

}