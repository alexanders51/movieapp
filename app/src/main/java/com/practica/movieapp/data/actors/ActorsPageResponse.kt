package com.practica.movieapp.data.actors

import com.google.gson.annotations.SerializedName

data class ActorsPageResponse(
    @SerializedName("page") var page: Int,
    @SerializedName("results") var results: List<ActorResponse>,
    @SerializedName("total_results") var totalResults: Int,
    @SerializedName("total_pages") var totalPages: Int
)