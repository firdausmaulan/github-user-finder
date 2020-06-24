package com.fd.guf.di

import com.fd.guf.BuildConfig
import com.fd.guf.dataSource.remote.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesHttpClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
            //if (BuildConfig.DEBUG) addInterceptor(ChuckInterceptor(context))
            addInterceptor { chain ->
                val request = chain.request()
                val newRequest = request.newBuilder()
                    .addHeader("Accept", "application/json")
                    .build()
                chain.proceed(newRequest)
            }
        }.build()

    @Singleton
    @Provides
    fun provideClient(): Retrofit =
        Retrofit.Builder().apply {
            client(providesHttpClient())
            baseUrl(BuildConfig.BASE_URL)
            addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            addConverterFactory(GsonConverterFactory.create())
        }.build()

    @Provides
    fun provideService(): ApiService {
        return provideClient().create(ApiService::class.java)
    }

    /*@Singleton
    @Provides
    fun provideClient(): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        val client = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) client.addInterceptor(ChuckInterceptor(BaseApp.context))

        client.addInterceptor { chain ->
            val request = chain.request()
            val newRequest = request.newBuilder()
                .addHeader("Accept", "application/json")
                .build()
            chain.proceed(newRequest)
        }

        return retrofit.client(client.build()).build()
    }*/
}