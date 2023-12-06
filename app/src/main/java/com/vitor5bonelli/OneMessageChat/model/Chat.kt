package com.vitor5bonelli.OneMessageChat.model

import java.util.UUID
import com.google.firebase.database.Exclude

data class Chat(
    var id: String = "",
    var message: String = "",
    var members: List<String> = emptyList()
) {
    constructor() : this("", "", emptyList())

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "message" to message,
            "members" to members
        )
    }
}
