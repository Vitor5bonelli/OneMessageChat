package com.vitor5bonelli.OneMessageChat.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vitor5bonelli.OneMessageChat.model.Chat

class ChatRepository {
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Chats")

    // Adicionar um novo chat
    fun addChat(chat: Chat) {
        val chatId = databaseReference.push().key
        chatId?.let {
            chat.id = it
            databaseReference.child(it).setValue(chat)
        }
    }

    // Obter detalhes de um chat por ID
    fun getChatById(chatId: String, callback: (Chat?) -> Unit) {
        databaseReference.child(chatId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val chat = dataSnapshot.getValue(Chat::class.java)
                callback.invoke(chat)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                callback.invoke(null)
            }
        })
    }

    // Atualizar detalhes de um chat existente
    fun updateChat(chat: Chat) {
        databaseReference.child(chat.id).setValue(chat)
    }

    // Deletar um chat por ID
    fun deleteChat(chatId: String) {
        databaseReference.child(chatId).removeValue()
    }
}