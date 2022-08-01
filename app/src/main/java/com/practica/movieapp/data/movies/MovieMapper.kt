package com.practica.movieapp.data.movies

class MovieMapper {
    fun map(response: MovieResponse): Movie = Movie(
        id = response.id,
        originalTitle  = response.originalTitle,
        title          = response.title,
        overview       = response.overview,
        releaseDate    = response.releaseDate,
        adult          = response.adult,
        posterPath     = response.posterPath,
        backdropPath   = response.backdropPath,
        isFavorite     = false,
        isWatched      = false
    )
}