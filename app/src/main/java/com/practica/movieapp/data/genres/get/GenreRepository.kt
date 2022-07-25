package com.practica.movieapp.data.genres.get

import com.practica.movieapp.data.genres.Genre
import com.practica.movieapp.database.Database
import com.practica.movieapp.network.ApiClient

class GenreRepository private constructor() {
    companion object {
        val instance = GenreRepository()
    }

    private val genresRemoteDataSource = GenresRemoteDataSource(ApiClient.instance.retrofit!!)
    private val genresLocalDataSource = GenresLocalDataSource(Database.instance)

    fun getAllRemoteGenres() = genresRemoteDataSource.getGenres()

    fun getAllLocalGenres() = genresLocalDataSource.getAll()
    fun saveLocal(genre: Genre) = genresLocalDataSource.save(genre)
    fun saveAllLocal(genres: List<Genre>) = genresLocalDataSource.saveAll(genres)
    fun deleteLocal(genre: Genre) = genresLocalDataSource.delete(genre)
    fun deleteAllLocal() = genresLocalDataSource.deleteAll()
    fun deleteAllLocal(genres: List<Genre>) = genresLocalDataSource.deleteAll(genres)
    fun replaceAllLocal(genres: List<Genre>) = genresLocalDataSource.replaceAll(genres)
}