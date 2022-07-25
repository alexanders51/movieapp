package com.practica.movieapp.data.actors.get

import com.practica.movieapp.data.actors.Actor
import com.practica.movieapp.data.actors.ActorMapper
import com.practica.movieapp.network.CallExtensions.executeAndDeliver
import com.practica.movieapp.utils.Constants
import retrofit2.Retrofit

class ActorsRemoteDataSource(retrofit: Retrofit) {
    private val apiService: ActorsApiService = retrofit.create(ActorsApiService::class.java)
    private val actorMapper: ActorMapper = ActorMapper()

    fun getActors(page: Int): List<Actor> {
        return apiService.getActors(Constants.API_KEY, Constants.LANGUAGE, page.toString())
                .executeAndDeliver()
                .results
                .map { actorMapper.map(it) }
    }
}