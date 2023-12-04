package com.vitor5bonelli.OneMessageChat.model

import java.util.UUID

data class Pessoa(
    val id: UUID = UUID.randomUUID(),
    val nome: String,
    val senha: String,
    val conversasInscritas: List<String>
)