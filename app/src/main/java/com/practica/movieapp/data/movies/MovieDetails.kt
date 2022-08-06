package com.practica.movieapp.data.movies

import com.practica.movieapp.data.genres.Genre

data class MovieDetails (
    var id: Int,
    var adult: Boolean,
    var backdropPath: String?,
    var budget: Long,
    var genres: List<Genre>,
    var homepage: String?,
    var originalTitle: String,
    var overview: String,
    var popularity: Number,
    var posterPath: String?,
    var releaseDate: String?,
    var status: String,
    var title: String,
    var voteAverage: Number,
    var voteCount: Int,
    var videos: List<MovieVideos>
) {
    fun toMovie() = Movie(
        id = this.id,
        adult = this.adult,
        backdropPath = this.backdropPath,
        originalTitle = this.originalTitle,
        title = this.title,
        overview = this.overview,
        releaseDate = this.releaseDate,
        posterPath = this.posterPath,
        isFavorite = false,
        isWatched = false
    )
}