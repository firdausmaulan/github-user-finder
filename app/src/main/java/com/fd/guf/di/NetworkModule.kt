package com.fd.guf.di

import android.content.Context
import com.fd.guf.BuildConfig
import com.fd.guf.dataSource.remote.ApiService
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class NetworkModule {

    @Singleton
    @Provides
    fun providesHttpClient(context: Context): OkHttpClient =
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) addInterceptor(ChuckInterceptor(context))
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
    fun provideClient(context: Context): Retrofit =
        Retrofit.Builder().apply {
            client(providesHttpClient(context))
            baseUrl(BuildConfig.BASE_URL)
            addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            addConverterFactory(GsonConverterFactory.create())
        }.build()

    @Provides
    fun provideService(context: Context): ApiService {
        return provideClient(context).create(ApiService::class.java)
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