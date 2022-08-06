package com.practica.movieapp.data.movies.db

import androidx.room.*
import com.practica.movieapp.data.movies.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAll(): List<Movie>

    @Insert
    fun save(movie: Movie)

    @Insert
    fun saveAll(movies: List<Movie>)

    @Delete
    fun delete(movie: Movie)

    @Delete
    fun deleteAll(movies: List<Movie>)

    @Query("DELETE FROM movie")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM movie")
    fun size(): Int

    @Transaction
    fun replaceAll(movies: List<Movie>) {
        deleteAll()
        saveAll(movies)
    }

    @Query("SELECT * FROM movie WHERE is_favorite = 1")
    fun getFavorite(): List<Movie>

    @Query("SELECT * FROM movie WHERE is_watched = 1")
    fun getWatched(): List<Movie>
}
