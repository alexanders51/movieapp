package com.practica.movieapp.data.genres

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre")
data class Genre(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")           var id: Int,
    @ColumnInfo(name = "name")         var name: String,
    @ColumnInfo(name = "is_selected")  var isSelected: Boolean
) {
    override fun equals(other: Any?): Boolean =
        (other is Genre) && id == other.id && name == other.name
}