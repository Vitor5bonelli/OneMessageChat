package com.vitor5bonelli.OneMessageChat.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ChildEventListener
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
import com.vitor5bonelli.OneMessageChat.repository.UserRepository
import java.util.UUID

class ChatListActivity : AppCompatActivity() {
    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Chats")
    private var databaseUsers: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
    private var userRepo: UserRepository = UserRepository()

    private lateinit var binding: ActivityChatListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        val userEmail = intent.getStringExtra("email")

        databaseUsers.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var userId: String? = null

                for (userSnapshot in dataSnapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)

                    if (user != null && user.email == userEmail) {
                        userId = user.id

                        val userChats = user.subscribedChats

                        val chatList = mutableListOf<Chat>()
                        val adapter = ChatListAdapter(context = this@ChatListActivity, chats = chatList)
                        val recyclerView = binding.recyclerView
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = LinearLayoutManager(this@ChatListActivity)

                        adapter.onItemClick = {
                            val intent = Intent(this@ChatListActivity, EditChatActivity::class.java)
                            intent.putExtra("chat", it)
                            startActivity(intent)
                        }

                        val chatEventListener = object : ChildEventListener {
                            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                                val chat = snapshot.getValue(Chat::class.java)

                                if (chat != null && chat.id in userChats) {
                                    chatList.add(chat)
                                    adapter.notifyDataSetChanged()
                                }
                            }

                            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                                val chat = snapshot.getValue(Chat::class.java)
                                val chatId = snapshot.key

                                val index = chatList.indexOfFirst { it.id == chatId }
                                if (index != -1 && chat != null) {
                                    chatList[index] = chat
                                    adapter.notifyItemChanged(index)
                                }
                            }

                            override fun onChildRemoved(snapshot: DataSnapshot) {
                                val chatId = snapshot.key

                                val index = chatList.indexOfFirst { it.id == chatId }
                                if (index != -1) {
                                    chatList.removeAt(index)
                                    adapter.notifyItemRemoved(index)
                                }
                            }

                            override fun onChildMoved(
                                snapshot: DataSnapshot,
                                previousChildName: String?
                            ) {
                                TODO("Not yet implemented")
                            }

                            // Implemente os outros métodos do ChildEventListener conforme necessário

                            override fun onCancelled(error: DatabaseError) {
                                Log.e("ERROR", "Error fetching chat: ${error.message}")
                            }
                        }

                        database.addChildEventListener(chatEventListener)

                        binding.createGroupBTN.setOnClickListener {
                            val intent = Intent(this@ChatListActivity, CreateChatActivity::class.java)
                            intent.putExtra("userId", userId)
                            startActivity(intent)
                        }

                        binding.enterGroupBTN.setOnClickListener {
                            val intent = Intent(this@ChatListActivity, EnterChatActivity::class.java)
                            intent.putExtra("userId", userId)
                            startActivity(intent)
                        }

                        binding.exitBTN.setOnClickListener{
                            finish()
                        }
                    }
                }

                if (userId == null) {
                    Log.e("ERROR", "User with $userEmail not found.")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("ERROR", "Error fetching user: ${databaseError.message}")
            }
        })

    }

}