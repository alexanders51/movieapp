package com.practica.movieapp.data

import com.practica.movieapp.data.actors.Actor
import com.practica.movieapp.data.actors.get.ActorRepository
import com.practica.movieapp.data.genres.Genre
import com.practica.movieapp.data.genres.get.GenreRepository
import com.practica.movieapp.data.movies.Movie
import com.practica.movieapp.data.movies.get.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DataHandler {
    const val ACTOR_PAGE_NR = 1

    private var genres: List<Genre> = emptyList()
    private var actors: List<Actor> = emptyList()
    private var movies: List<Movie> = emptyList()

    private var genresLocal: List<Genre> = emptyList()
    private var actorsLocal: List<Actor> = emptyList()
    private var moviesLocal: List<Movie> = emptyList()

    private var actorRep = ActorRepository.instance
    private var genreRep = GenreRepository.instance
    private var movieRep = MoviesRepository.instance

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    fun initialize() {
        CoroutineScope(ioDispatcher).launch {
            genres = genreRep.getAllRemoteGenres()
            actors = actorRep.getRemoteActorsFromPage(ACTOR_PAGE_NR)

            updateLocal()
            updateMovies()
        }
    }

    fun updateMovies() {
        val genreIds = genreRep.getAllLocalGenres().map { it.id }
        val actorIds = actorRep.getAllLocalActors().map { it.id }

        movies = movieRep.getAllRemoteMovies(
            ACTOR_PAGE_NR,
            actorIds.toTypedArray(),
            genreIds.toTypedArray()
        )
    }

    fun updateLocal() {
        genresLocal = genreRep.getAllLocalGenres()
        actorsLocal = actorRep.getAllLocalActors()
        moviesLocal = movieRep.getAllLocalMovies()
    }

    fun getPreloadedGenres() = genres
    fun getPreloadedActors() = actors
    fun getPreloadedMovies() = movies

    fun getLocalGenres() = genresLocal
    fun getLocalActors() = actorsLocal
    fun getLocalMovies() = moviesLocal

    fun queryMovies(query: String): List<Movie> = movieRep.getRemoteMoviesBySearchQuery(ACTOR_PAGE_NR, query)

    fun userPreferencesExist() =
        (actorRep.getCount() != 0) && (genreRep.getCount() != 0)
}