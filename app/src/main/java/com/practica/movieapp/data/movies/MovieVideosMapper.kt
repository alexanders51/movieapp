package com.practica.movieapp.data.movies

class MovieVideosMapper {
    fun map(response: MovieVideosResponse) = MovieVideos(
        name = response.name,
        key = response.key,
        site = response.site,
        type = response.type
    )
}