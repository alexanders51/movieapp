package com.practica.movieapp.data.movies

import com.practica.movieapp.data.genres.Genre
import com.practica.movieapp.data.genres.GenreMapper

class MovieDetailsMapper {
    private var genreMapper: GenreMapper = GenreMapper()
    private var movieVideosMapper: MovieVideosMapper = MovieVideosMapper()

    fun map(response: MovieDetailsResponse): MovieDetails {
        val genres: MutableList<Genre> = mutableListOf()
        val videos: MutableList<MovieVideos> = mutableListOf()

        response.genres.forEach {
            genres.add(genreMapper.map(it))
        }
        response.videos.results.forEach {
            videos.add(movieVideosMapper.map(it))
        }

        return MovieDetails(
            id = response.id,
            adult = response.adult,
            backdropPath = response.backdropPath,
            budget = response.budget,
            genres = genres,
            homepage = response.homepage,
            originalTitle = response.originalTitle,
            overview = response.overview,
            popularity = response.popularity,
            posterPath = response.posterPath,
            releaseDate = response.releaseDate,
            status = response.status,
            title = response.title,
            voteAverage = response.voteAverage,
            voteCount = response.voteCount,
            videos = videos
        )
    }
}