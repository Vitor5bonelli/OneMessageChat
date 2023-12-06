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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        val chatList = mutableListOf<Chat>()
        val adapter = ChatListAdapter(context = this, chats = chatList)
        var recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.onItemClick = {
            val intent = Intent(this, EditChatActivity::class.java)
            intent.putExtra("chat", it)
            startActivity(intent)
        }

        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    for (chatSnapshot in snapshot.children){
                        val chat = chatSnapshot.getValue(Chat::class.java)
                        chatList.add(chat!!)
                    }

                    adapter.notifyDataSetChanged()

                }
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.exitBTN.setOnClickListener{
            finish()
        }

        binding.createGroupBTN.setOnClickListener{
            switchToCreateChatView()
        }

        binding.enterGroupBTN.setOnClickListener{
            switchToEnterChatView()
        }

    }

    private fun switchToCreateChatView(){
        val intent = Intent(this, CreateChatActivity::class.java)
        saveUserId(intent)
        startActivity(intent)
    }

    private fun switchToEnterChatView(){
        val intent = Intent(this, EnterChatActivity::class.java)
        saveUserId(intent)
        startActivity(intent)
    }

    private fun saveToIntentUserIdFromEmail(email: String, intent: Intent){
        val databaseUser: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

        fun getIdFromEmail(email: String) {
            databaseUser.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (userSnapshot in dataSnapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        if (user?.email == email) {
                            val userId = user.id
                            intent.putExtra("userId", userId)
                            break
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("Erro while searching for user: ${databaseError.message}")
                }
            })
        }

    }

    private fun saveUserId(intent: Intent){
        val userEmail = intent.getStringExtra("email")
        if (userEmail != null) {
            saveToIntentUserIdFromEmail(userEmail, intent)
        }
    }
}