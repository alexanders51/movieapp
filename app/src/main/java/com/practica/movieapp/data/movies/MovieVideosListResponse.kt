package com.practica.movieapp.data.movies

import com.google.gson.annotations.SerializedName

data class MovieVideosListResponse (
    @SerializedName("results") var results: List<MovieVideosResponse>
)