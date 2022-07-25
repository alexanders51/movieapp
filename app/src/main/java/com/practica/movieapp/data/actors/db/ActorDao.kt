package com.practica.movieapp.data.actors.db

import androidx.room.*
import com.practica.movieapp.data.actors.Actor

@Dao
interface ActorDao {
    @Query("SELECT * FROM actor")
    fun getAll(): List<Actor>

    @Insert
    fun save(actor: Actor)

    @Insert
    fun saveAll(actors: List<Actor>)

    @Delete
    fun delete(actor: Actor)

    @Delete
    fun deleteAll(actors: List<Actor>)

    @Query("DELETE FROM actor")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM actor")
    fun size(): Int

    @Transaction
    fun replaceAll(actors: List<Actor>) {
        deleteAll()
        saveAll(actors)
    }
}
