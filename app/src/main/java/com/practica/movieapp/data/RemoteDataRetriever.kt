package com.practica.movieapp.data

import com.practica.movieapp.data.actors.Actor
import com.practica.movieapp.data.actors.get.ActorRepository
import com.practica.movieapp.data.genres.Genre
import com.practica.movieapp.data.genres.get.GenreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object RemoteDataRetriever {
    const val ACTOR_PAGE_NR = 1

    private var genres: List<Genre> = emptyList()
    private var actors : List<Actor> = emptyList()
    private var actorRep = ActorRepository.instance
    private var genreRep = GenreRepository.instance

    fun initialize() {
        GlobalScope.launch(Dispatchers.IO) {
            genres = genreRep.getAllRemoteGenres()
            actors = actorRep.getRemoteActorsFromPage(ACTOR_PAGE_NR)
        }
    }

    fun getPreloadedGenres() = genres
    fun getPreloadedActors() = actors
}