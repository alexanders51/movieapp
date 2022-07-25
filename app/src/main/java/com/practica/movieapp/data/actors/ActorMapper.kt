package com.practica.movieapp.data.actors

class ActorMapper {
    fun map(response: ActorResponse): Actor = Actor(
        id = response.id,
        name = response.name,
        profilePath = response.profilePath,
        adult = response.adult,
        popularity = response.popularity,
        isSelected = false
    )
}