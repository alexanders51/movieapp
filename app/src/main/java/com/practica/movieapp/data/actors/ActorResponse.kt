package com.practica.movieapp.data.actors

import com.google.gson.annotations.SerializedName

data class ActorResponse (
    @SerializedName("profile_path")         var profilePath: String?,
    @SerializedName("adult")                var adult: Boolean,
    @SerializedName("id")                   var id: Int,
    @SerializedName("name")                 var name: String,
    @SerializedName("popularity")           var popularity: Double
)