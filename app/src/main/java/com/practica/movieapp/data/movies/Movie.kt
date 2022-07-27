package com.practica.movieapp.data.movies

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre")
data class Movie(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")              var id: Int,
    @ColumnInfo(name = "original_title")  var originalTitle: String,
    @ColumnInfo(name = "title")           var title: String,
    @ColumnInfo(name = "overview")        var overview: String,
    @ColumnInfo(name = "release_date")    var releaseDate: String,
    @ColumnInfo(name = "adult")           var adult: Boolean,
    @ColumnInfo(name = "poster_path")     var posterPath: String?,
    @ColumnInfo(name = "backdrop_path")   var backdropPath: String?,
) {
    override fun equals(other: Any?): Boolean =
        (other is Movie) && id == other.id && originalTitle == other.originalTitle &&
                title == other.title && overview == other.overview && releaseDate == other.releaseDate &&
                adult == other.adult && posterPath == other.posterPath && backdropPath == other.backdropPath
}