package com.practica.movieapp.data.genres.db

import androidx.room.*
import com.practica.movieapp.data.genres.Genre

@Dao
interface GenreDao {
    @Query("SELECT * FROM genre")
    fun getAll(): List<Genre>

    @Insert
    fun save(genre: Genre)

    @Insert
    fun saveAll(genres: List<Genre>)

    @Delete
    fun delete(genre: Genre)

    @Delete
    fun deleteAll(genres: List<Genre>)

    @Query("DELETE FROM genre")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM genre")
    fun size(): Int

    @Transaction
    fun replaceAll(genres: List<Genre>) {
        deleteAll()
        saveAll(genres)
    }
}