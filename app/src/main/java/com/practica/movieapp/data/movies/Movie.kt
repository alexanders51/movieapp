package com.practica.movieapp.data.movies

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "adult") var adult: Boolean,
    @ColumnInfo(name = "backdrop_path") var backdropPath: String?,
    @ColumnInfo(name = "original_title") var originalTitle: String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "overview") var overview: String,
    @ColumnInfo(name = "release_date") var releaseDate: String?,
    @ColumnInfo(name = "poster_path") var posterPath: String?,
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean,
    @ColumnInfo(name = "is_watched") var isWatched: Boolean,
) {
    override fun equals(other: Any?): Boolean =
        (other is Movie) && id == other.id && originalTitle == other.originalTitle &&
                title == other.title && overview == other.overview && releaseDate == other.releaseDate &&
                adult == other.adult && posterPath == other.posterPath && backdropPath == other.backdropPath

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + originalTitle.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + overview.hashCode()
        result = 31 * result + (releaseDate?.hashCode() ?: 0)
        result = 31 * result + adult.hashCode()
        result = 31 * result + (posterPath?.hashCode() ?: 0)
        result = 31 * result + (backdropPath?.hashCode() ?: 0)
        return result
    }
}