package com.alperenbabagil.cleanappbase.core.data

import com.alperenbabagil.cabdata.ApiCallAdapter
import com.alperenbabagil.cleanappbase.core.data.CoreDataConstants.Companion.BASE_URL
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataCoreModule = module {

    factory {
        CABDemoApiCallAdapter() as ApiCallAdapter
    }

    single {
        OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level= HttpLoggingInterceptor.Level.BODY})
            .build()
    }

    factory {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}