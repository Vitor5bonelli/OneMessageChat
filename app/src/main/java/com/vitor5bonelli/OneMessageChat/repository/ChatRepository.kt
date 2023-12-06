package com.vitor5bonelli.OneMessageChat.repository

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.vitor5bonelli.OneMessageChat.model.Chat

class ChatRepository {
    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Chats")

    fun createChat(chat: Chat){
        chat.id?.let {
            database.child(it).setValue(chat).addOnSuccessListener {
                Log.i("Chat", "Created with sucess!")
            }.addOnFailureListener {
                Log.i("Chat", "Creation failed!")
            }
        }
    }

}