package com.practica.movieapp.data.movies

import com.google.gson.annotations.SerializedName

data class MoviesPageResponse (
    @SerializedName("page")           var page: Int,
    @SerializedName("results")        var results: List<MovieResponse>,
    @SerializedName("total_results")  var totalResults: Int,
    @SerializedName("total_pages")    var totalPages: Int
)