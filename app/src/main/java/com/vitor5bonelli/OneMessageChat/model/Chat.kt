package com.vitor5bonelli.OneMessageChat.model

import java.util.UUID

data class Chat (
    var id: String,
    val message: String,
    val members: List<UUID> = listOf()
)