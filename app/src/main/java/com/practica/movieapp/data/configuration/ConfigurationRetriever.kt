package com.practica.movieapp.data.configuration

import com.practica.movieapp.network.CallExtensions.executeAndDeliver
import com.practica.movieapp.utils.Constants
import retrofit2.Retrofit

class ConfigurationRetriever(retrofit: Retrofit) {
    private val configApiService: ConfigurationApiService = retrofit.create(ConfigurationApiService::class.java)

    fun getConfig(): ConfigurationResponse = configApiService.getActors(Constants.API_KEY).executeAndDeliver()
}