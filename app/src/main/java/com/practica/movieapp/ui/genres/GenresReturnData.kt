package com.practica.movieapp.ui.genres

import android.os.Parcel
import android.os.Parcelable

data class GenresReturnData(
    var nrSelectedGenres: Int,
    var selectedGenresList: Array<String>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.createStringArray()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(nrSelectedGenres)
        parcel.writeStringArray(selectedGenresList)
    }

    override fun describeContents() = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GenresReturnData

        if (nrSelectedGenres != other.nrSelectedGenres) return false
        if (!selectedGenresList.contentEquals(other.selectedGenresList)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nrSelectedGenres
        result = 31 * result + selectedGenresList.contentHashCode()
        return result
    }

    companion object CREATOR : Parcelable.Creator<GenresReturnData> {
        override fun createFromParcel(parcel: Parcel): GenresReturnData {
            return GenresReturnData(parcel)
        }

        override fun newArray(size: Int): Array<GenresReturnData?> {
            return arrayOfNulls(size)
        }
    }
}