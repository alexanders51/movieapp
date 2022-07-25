package com.practica.movieapp.network

import com.practica.movieapp.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient private constructor() {
    companion object {
        val instance = ApiClient()
    }

    private val loggingInterceptor: LoggingInterceptor by lazy {
        LoggingInterceptor().apply {
            this.setLevel(LoggingInterceptor.Level.BODY)
        }
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    var retrofit : Retrofit? = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}