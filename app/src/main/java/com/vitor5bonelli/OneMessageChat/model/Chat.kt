package com.vitor5bonelli.OneMessageChat.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.Exclude
import java.util.ArrayList

data class Chat(
    var id: String? = "",
    var message: String? = "",
    var members: ArrayList<String>? = arrayListOf<String>()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList()
    )

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "message" to message,
            "members" to members
        )
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(message)
        parcel.writeStringList(members)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Chat> {
        override fun createFromParcel(parcel: Parcel): Chat {
            return Chat(parcel)
        }

        override fun newArray(size: Int): Array<Chat?> {
            return arrayOfNulls(size)
        }
    }
}
