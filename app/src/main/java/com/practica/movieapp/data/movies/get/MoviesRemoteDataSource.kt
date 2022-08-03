package com.practica.movieapp.data.movies.get

import com.practica.movieapp.data.movies.Movie
import com.practica.movieapp.data.movies.MovieMapper
import com.practica.movieapp.network.CallExtensions.executeAndDeliver
import com.practica.movieapp.utils.Constants
import retrofit2.Retrofit

class MoviesRemoteDataSource(retrofit: Retrofit) {
    private val apiService: MoviesApiService = retrofit.create(MoviesApiService::class.java)
    private val movieMapper: MovieMapper = MovieMapper()

    fun retrieveDiscovery(page: Int, actorIds: Array<Int>, genreIds: Array<Int>): List<Movie> {
        val withCastStr = actorIds.joinToString(separator = "|") { i -> i.toString() }
        val withGenresStr = genreIds.joinToString(separator = "|") { i -> i.toString() }
        return apiService.getMoviesFromDiscoveryApi(
            Constants.API_KEY,
            Constants.LANGUAGE,
            page.toString(),
            withCastStr,
            withGenresStr
        )
            .executeAndDeliver()
            .results
            .map { movieMapper.map(it) }
    }

    fun retrieveSearch(page: Int, query: String) = apiService.getMoviesFromSearchApi(
        Constants.API_KEY,
        Constants.LANGUAGE,
        page.toString(),
        query
    )
        .executeAndDeliver()
        .results
        .map { movieMapper.map(it) }
}