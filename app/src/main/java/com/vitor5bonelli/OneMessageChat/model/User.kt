package com.vitor5bonelli.OneMessageChat.model

import java.util.UUID

data class User(
    val id: UUID = UUID.randomUUID(),
    val username: String,
    val email: String,
    val password: String,
    val subscribedChats: List<String>
)