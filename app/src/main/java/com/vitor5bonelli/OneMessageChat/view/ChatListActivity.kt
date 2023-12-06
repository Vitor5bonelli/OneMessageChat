package com.vitor5bonelli.OneMessageChat.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.vitor5bonelli.OneMessageChat.controller.ChatListAdapter
import com.vitor5bonelli.OneMessageChat.databinding.ActivityChatListBinding
import com.vitor5bonelli.OneMessageChat.model.Chat
import com.vitor5bonelli.OneMessageChat.model.User
import com.vitor5bonelli.OneMessageChat.repository.ChatRepository
import java.util.UUID

class ChatListActivity : AppCompatActivity() {
    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Chats")
    private lateinit var binding: ActivityChatListBinding
    private var chatRepo: ChatRepository = ChatRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        binding.exitBTN.setOnClickListener{
            finish()
        }

        binding.createGroupBTN.setOnClickListener{
            switchToCreateChatView()
        }

        binding.enterGroupBTN.setOnClickListener{
            switchToEnterChatView()
        }

        val placeholderChats = mutableListOf<Chat>(
            Chat(
                id = "GrupoMassa",
                message = "Só os brabo aqui",
                members = listOf(
                    "djsaioJ#joijdsA",
                    "djsaioJ#joijdsA2",
                    "djsaioJ#joijdsA3"

                )),
            Chat(
                id = "JogosOnline",
                message = "Jogos Gratis",
                members = listOf(
                    "djsaioJ#joijdsA",
                    "djsaioJ#joijdsA2",
                    "djsaioJ#joijdsA3"
                )
            )
        )

        val chatList = mutableListOf<Chat>()

        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    for (chatSnapshot in snapshot.children){
                        var chat = chatSnapshot.getValue(Chat::class.java)
                        chatList.add(chat!!)
                    }

                }
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        val recyclerView = binding.recyclerView
        recyclerView.adapter = ChatListAdapter(context = this, chats = chatList)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun switchToCreateChatView(){
        val intent = Intent(this, CreateChatActivity::class.java)
        startActivity(intent)
    }

    private fun switchToEnterChatView(){
        val intent = Intent(this, EnterChatActivity::class.java)
        startActivity(intent)
    }

}