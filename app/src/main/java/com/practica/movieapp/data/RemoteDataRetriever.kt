package com.practica.movieapp.data

import com.practica.movieapp.data.actors.Actor
import com.practica.movieapp.data.actors.get.ActorRepository
import com.practica.movieapp.data.genres.Genre
import com.practica.movieapp.data.genres.get.GenreRepository
import com.practica.movieapp.data.movies.Movie
import com.practica.movieapp.data.movies.get.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object RemoteDataRetriever {
    const val ACTOR_PAGE_NR = 1

    private var genres: List<Genre> = emptyList()
    private var actors : List<Actor> = emptyList()
    private var movies : List<Movie> = emptyList()
    private var actorRep = ActorRepository.instance
    private var genreRep = GenreRepository.instance
    private var movieRep = MoviesRepository.instance

    fun initialize() {
        GlobalScope.launch(Dispatchers.IO) {
            genres = genreRep.getAllRemoteGenres()
            actors = actorRep.getRemoteActorsFromPage(ACTOR_PAGE_NR)

            updateMovies()
        }
    }

    fun updateMovies() {
        val genreIds = genreRep.getAllLocalGenres().map { it.id }
        val actorIds = actorRep.getAllLocalActors().map { it.id }

        movies = movieRep.getMovies(ACTOR_PAGE_NR, actorIds.toTypedArray(), genreIds.toTypedArray())
    }

    fun getPreloadedGenres() = genres
    fun getPreloadedActors() = actors
    fun getPreloadedMovies() = movies

    fun userPreferencesExist() =
        (actorRep.getCount() != 0) && (genreRep.getCount() != 0)
}