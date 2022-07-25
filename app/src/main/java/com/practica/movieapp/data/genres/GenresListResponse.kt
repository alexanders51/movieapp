package com.practica.movieapp.data.genres

import com.google.gson.annotations.SerializedName

class GenresListResponse(
    @SerializedName("genres")
    var genres: List<GenreResponse> = emptyList()
)