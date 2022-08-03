package com.practica.movieapp.data.genres

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String
)