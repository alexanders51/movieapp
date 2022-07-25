package com.practica.movieapp.data.genres.get

import com.practica.movieapp.data.genres.Genre
import com.practica.movieapp.data.genres.db.GenreDao
import com.practica.movieapp.database.Database

class GenresLocalDataSource(database: Database) {
    private val genreDao: GenreDao = database.movieAppDb.genreDao()

    fun getAll() = genreDao.getAll()
    fun save(genre: Genre) = genreDao.save(genre)
    fun saveAll(genres: List<Genre>) = genreDao.saveAll(genres)
    fun delete(genre: Genre) = genreDao.delete(genre)
    fun deleteAll() = genreDao.deleteAll()
    fun deleteAll(genres: List<Genre>) = genreDao.deleteAll(genres)
    fun replaceAll(genres: List<Genre>) = genreDao.replaceAll(genres)
    fun size() = genreDao.size()
}