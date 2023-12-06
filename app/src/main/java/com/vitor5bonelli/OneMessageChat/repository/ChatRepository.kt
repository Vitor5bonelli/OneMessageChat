package com.vitor5bonelli.OneMessageChat.repository

import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
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

    fun getAllChats() {

    }

}