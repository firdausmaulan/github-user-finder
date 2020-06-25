package com.fd.guf.dataSource.remote

import android.content.Context
import com.fd.guf.BuildConfig
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient constructor(private val context: Context) {

    fun getClient(): Retrofit {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)

        val client = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) client.addInterceptor(ChuckInterceptor(context))

        client.addInterceptor { chain ->
            val request = chain.request()
            val newRequest = request.newBuilder()
                .addHeader("Accept", "application/json")
                .build()
            chain.proceed(newRequest)
        }
        return retrofit.client(client.build()).build()
    }


    fun getService(): ApiService {
        return getClient().create(ApiService::class.java)
    }
}