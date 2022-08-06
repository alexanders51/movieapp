package com.practica.movieapp.data.movies

import com.google.gson.annotations.SerializedName

data class MovieVideosResponse (
    @SerializedName("name") var name: String,
    @SerializedName("key") var key: String,
    @SerializedName("site") var site: String,
    @SerializedName("type") var type: String
)