package com.practica.movieapp.data.genres

class GenreMapper {
    fun map(response: GenreResponse) : Genre = Genre(
        id = response.id,
        name = response.name,
        isSelected = false
    )
}