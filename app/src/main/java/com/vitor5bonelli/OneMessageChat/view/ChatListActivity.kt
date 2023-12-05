package com.vitor5bonelli.OneMessageChat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vitor5bonelli.OneMessageChat.controller.ChatListAdapter
import com.vitor5bonelli.OneMessageChat.databinding.ActivityChatListBinding
import com.vitor5bonelli.OneMessageChat.model.Chat
import com.vitor5bonelli.OneMessageChat.model.User

class ChatListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        val chats = mutableListOf<Chat>()

        val userUuid = intent.getStringExtra("uuid")

        if (userUuid != null) {

            getAllUserChats(userUuid) { subscribedChats ->
                if (subscribedChats.isNotEmpty()) {
                    for (chatId in subscribedChats) {
                        getChatById(chatId) { chat ->
                            if (chat != null) {
                                chats.add(
                                    Chat(
                                        chat.id,
                                        chat.message,
                                        chat.members
                                    )
                                )
                            } else {
                                Log.d("ChatSearch:", "Chat with $chatId not found.")
                            }
                        }
                    }
                }
            }

        }

        val recyclerView = binding.recyclerView
        recyclerView.adapter = ChatListAdapter(context = this, chats = chats)
    }

    private fun getAllUserChats(userUuid: String, callback: (List<String>) -> Unit) {
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

        databaseReference.child(userUuid).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(User::class.java)
                    val subscribedChats = user?.subscribedChats ?: emptyList()
                    callback.invoke(subscribedChats)
                } else {
                    callback.invoke(emptyList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback.invoke(emptyList())
            }
        })
    }

    private fun getChatById(chatId: String, callback: (Chat?) -> Unit) {
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Chats")

        databaseReference.child(chatId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chat = snapshot.getValue(Chat::class.java)
                callback.invoke(chat)
            }

            override fun onCancelled(error: DatabaseError) {
                callback.invoke(null)
            }
        })
    }

}