package com.practica.movieapp.data.movies.db

import androidx.room.*
import com.practica.movieapp.data.movies.MovieWatched

@Dao
interface MovieWatchedDao {
    @Query("SELECT * FROM movie_watched")
    fun getAll(): List<MovieWatched>

    @Insert
    fun save(movieWatched: MovieWatched)

    @Insert
    fun saveAll(moviesWatched: List<MovieWatched>)

    @Delete
    fun delete(movieWatched: MovieWatched)

    @Delete
    fun deleteAll(moviesWatched: List<MovieWatched>)

    @Query("DELETE FROM movie_watched")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM movie_watched")
    fun size(): Int

    @Transaction
    fun replaceAll(moviesWatched: List<MovieWatched>) {
        deleteAll()
        saveAll(moviesWatched)
    }
}
