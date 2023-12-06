package com.vitor5bonelli.OneMessageChat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vitor5bonelli.OneMessageChat.controller.ChatListAdapter
import com.vitor5bonelli.OneMessageChat.databinding.ActivityChatListBinding
import com.vitor5bonelli.OneMessageChat.model.Chat
import com.vitor5bonelli.OneMessageChat.model.User
import java.util.UUID

class ChatListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        binding.createGroupBTN.setOnClickListener{

        }

        binding.enterGroupBTN.setOnClickListener{

        }

        val placeholderChats = mutableListOf<Chat>(
            Chat(
                id = "ZapDosBrabo",
                message = "ZAPZAPZAPAZPAZPAPPA",
                members = listOf(
                    UUID.randomUUID(),
                    UUID.randomUUID(),
                    UUID.randomUUID()
                )),
            Chat(
                id = "IlhaDaMonkeizada",
                message = "become monke...",
                members = listOf(
                    UUID.randomUUID(),
                    UUID.randomUUID(),
                    UUID.randomUUID()
                )
            )
        )

        val recyclerView = binding.recyclerView
        recyclerView.adapter = ChatListAdapter(context = this, chats = placeholderChats)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

}