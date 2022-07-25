package com.practica.movieapp.data.actors.get

import com.practica.movieapp.data.actors.Actor
import com.practica.movieapp.data.actors.db.ActorDao
import com.practica.movieapp.database.Database

class ActorsLocalDataSource(database: Database) {
    private val actorDao: ActorDao = database.movieAppDb.actorDao()

    fun getAll() = actorDao.getAll()
    fun save(actor: Actor) = actorDao.save(actor)
    fun saveAll(actors: List<Actor>) = actorDao.saveAll(actors)
    fun delete(actor: Actor) = actorDao.delete(actor)
    fun deleteAll() = actorDao.deleteAll()
    fun deleteAll(actors: List<Actor>) = actorDao.deleteAll(actors)
    fun replaceAll(actors: List<Actor>) = actorDao.replaceAll(actors)
    fun size() = actorDao.size()
}
