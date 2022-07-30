package com.practica.movieapp.data.movies

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_favorite")
data class MovieFavorite (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")              override var id: Int,
    @ColumnInfo(name = "original_title")  override var originalTitle: String,
    @ColumnInfo(name = "title")           override var title: String,
    @ColumnInfo(name = "overview")        override var overview: String,
    @ColumnInfo(name = "release_date")    override var releaseDate: String?,
    @ColumnInfo(name = "adult")           override var adult: Boolean,
    @ColumnInfo(name = "poster_path")     override var posterPath: String?,
    @ColumnInfo(name = "backdrop_path")   override var backdropPath: String?,
    @ColumnInfo(name = "is_favorite")              var isFavorite: Boolean
) : MovieBase() {
    override fun equals(other: Any?): Boolean = super.equals(other)
}