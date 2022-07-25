package com.practica.movieapp.data.actors.get

import com.practica.movieapp.data.actors.Actor
import com.practica.movieapp.database.Database
import com.practica.movieapp.network.ApiClient

class ActorRepository private constructor() {
    companion object {
        val instance = ActorRepository()
    }

    private val actorsRemoteDataSource = ActorsRemoteDataSource(ApiClient.instance.retrofit!!)
    private val actorsLocalDataSource = ActorsLocalDataSource(Database.instance)

    fun getRemoteActorsFromPage(page: Int) = actorsRemoteDataSource.getActors(page)

    fun getAllLocalActors() = actorsLocalDataSource.getAll()
    fun saveLocal(actor: Actor) = actorsLocalDataSource.save(actor)
    fun saveAllLocal(actors: List<Actor>) = actorsLocalDataSource.saveAll(actors)
    fun deleteLocal(actor: Actor) = actorsLocalDataSource.delete(actor)
    fun deleteAllLocal() = actorsLocalDataSource.deleteAll()
    fun deleteAllLocal(actors: List<Actor>) = actorsLocalDataSource.deleteAll(actors)
    fun replaceAllLocal(actors: List<Actor>) = actorsLocalDataSource.replaceAll(actors)
}