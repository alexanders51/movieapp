package com.practica.movieapp.ui.actors

import android.os.Parcel
import android.os.Parcelable

data class ActorsReturnData(
    var nrSelectedActors: Int,
    var selectedActorsList: Array<String>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.createStringArray()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(nrSelectedActors)
        parcel.writeStringArray(selectedActorsList)
    }

    override fun describeContents() = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ActorsReturnData

        if (nrSelectedActors != other.nrSelectedActors) return false
        if (!selectedActorsList.contentEquals(other.selectedActorsList)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nrSelectedActors
        result = 31 * result + selectedActorsList.contentHashCode()
        return result
    }

    companion object CREATOR : Parcelable.Creator<ActorsReturnData> {
        override fun createFromParcel(parcel: Parcel): ActorsReturnData {
            return ActorsReturnData(parcel)
        }

        override fun newArray(size: Int): Array<ActorsReturnData?> {
            return arrayOfNulls(size)
        }
    }
}
