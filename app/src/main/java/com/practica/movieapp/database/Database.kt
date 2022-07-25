package com.practica.movieapp.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.practica.movieapp.data.actors.Actor
import com.practica.movieapp.data.actors.db.ActorDao
import com.practica.movieapp.data.genres.Genre
import com.practica.movieapp.data.genres.db.GenreDao

class Database private constructor() {
    companion object {
        val instance = Database()
    }

    @androidx.room.Database(entities = [Actor::class, Genre::class], version = 0)
    abstract class MovieAppDatabase : RoomDatabase() {
        abstract fun actorDao(): ActorDao
        abstract fun genreDao(): GenreDao
    }

    lateinit var movieAppDb: MovieAppDatabase private set

    fun initialize(context: Context) {
        this.movieAppDb = Room.databaseBuilder(
            context,
            MovieAppDatabase::class.java,
            "movieapp.db"
        ).fallbackToDestructiveMigration().build()
    }
}