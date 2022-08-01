package com.practica.movieapp.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.practica.movieapp.data.actors.Actor
import com.practica.movieapp.data.actors.db.ActorDao
import com.practica.movieapp.data.genres.Genre
import com.practica.movieapp.data.genres.db.GenreDao
import com.practica.movieapp.data.movies.Movie
import com.practica.movieapp.data.movies.db.MovieDao

class Database private constructor() {
    companion object {
        val instance = Database()
    }

    @androidx.room.Database(
        entities = [Actor::class, Genre::class, Movie::class],
        exportSchema = false,
        version = 2
    )
    abstract class MovieAppDatabase : RoomDatabase() {
        abstract fun actorDao(): ActorDao
        abstract fun genreDao(): GenreDao
        abstract fun movieDao(): MovieDao
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