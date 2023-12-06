package com.vitor5bonelli.OneMessageChat.model

import java.util.UUID

data class Chat (
    val id: String,
    val message: String,
    val members: List<UUID> = listOf()
)