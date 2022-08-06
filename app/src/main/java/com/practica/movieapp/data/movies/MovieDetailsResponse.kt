package com.practica.movieapp.data.movies

import com.google.gson.annotations.SerializedName
import com.practica.movieapp.data.genres.GenreResponse

data class MovieDetailsResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("adult") var adult: Boolean,
    @SerializedName("backdrop_path") var backdropPath: String?,
    @SerializedName("budget") var budget: Long,
    @SerializedName("genres") var genres: List<GenreResponse>,
    @SerializedName("homepage") var homepage: String?,
    @SerializedName("original_title") var originalTitle: String,
    @SerializedName("overview") var overview: String,
    @SerializedName("popularity") var popularity: Number,
    @SerializedName("poster_path") var posterPath: String?,
    @SerializedName("release_date") var releaseDate: String,
    @SerializedName("status") var status: String,
    @SerializedName("title") var title: String,
    @SerializedName("vote_average") var voteAverage: Number,
    @SerializedName("vote_count") var voteCount: Int,
    @SerializedName("videos") var videos: MovieVideosListResponse
)