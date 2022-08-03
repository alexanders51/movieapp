package com.practica.movieapp.data.actors

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "actor")
data class Actor(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "profile_path") var profilePath: String?,
    @ColumnInfo(name = "adult") var adult: Boolean,
    @ColumnInfo(name = "popularity") var popularity: Double,
    @ColumnInfo(name = "is_selected") var isSelected: Boolean
) {
    override fun equals(other: Any?): Boolean =
        (other is Actor) && id == other.id && name == other.name
                && profilePath == other.profilePath && adult == other.adult
                && popularity == other.popularity

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + (profilePath?.hashCode() ?: 0)
        result = 31 * result + adult.hashCode()
        result = 31 * result + popularity.hashCode()
        result = 31 * result + isSelected.hashCode()
        return result
    }
}