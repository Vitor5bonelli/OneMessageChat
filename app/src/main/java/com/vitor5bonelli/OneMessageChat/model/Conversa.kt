package com.vitor5bonelli.OneMessageChat.model

import java.util.UUID

data class Conversa (
    val id: String,
    val mensagem: String,
    val membros: List<UUID>
)