package com.practica.movieapp.data.movies

abstract class MovieBase {
    abstract var id: Int
    abstract var originalTitle: String
    abstract var title: String
    abstract var overview: String
    abstract var releaseDate: String?
    abstract var adult: Boolean
    abstract var posterPath: String?
    abstract var backdropPath: String?

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
