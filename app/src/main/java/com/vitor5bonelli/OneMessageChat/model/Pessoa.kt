package com.vitor5bonelli.OneMessageChat.model

import java.util.UUID

data class Pessoa(
    val id: UUID,
    val nome: String,
    val senha: String,
    val conversasInscritas: List<String>
)