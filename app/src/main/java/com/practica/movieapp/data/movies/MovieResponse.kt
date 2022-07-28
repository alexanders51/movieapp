package com.practica.movieapp.data.movies

import com.google.gson.annotations.SerializedName

data class MovieResponse (
    @SerializedName("id")              var id: Int,
    @SerializedName("original_title")  var originalTitle: String,
    @SerializedName("title")           var title: String,
    @SerializedName("overview")        var overview: String,
    @SerializedName("release_date")    var releaseDate: String?,
    @SerializedName("adult")           var adult: Boolean,
    @SerializedName("poster_path")     var posterPath: String?,
    @SerializedName("backdrop_path")   var backdropPath: String?,
)