package com.vitor5bonelli.OneMessageChat.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
    private var databaseUsers: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

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
        val recyclerView = binding.recyclerView
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

        val userEmail = intent.getStringExtra("email")


        binding.createGroupBTN.setOnClickListener{
            var userId: String? = null

            databaseUsers.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (userSnapshot in dataSnapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)

                        if (user != null) {
                            if (user.email == userEmail) {
                                userId = user.id

                                val intent = Intent(this@ChatListActivity, CreateChatActivity::class.java)
                                intent.putExtra("userId", userId)
                                startActivity(intent)
                            }
                        }

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("ERROR","Error fetching user: ${databaseError.message}")
                }
            })
        }

        binding.enterGroupBTN.setOnClickListener{

            var userId: String? = null

            databaseUsers.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (userSnapshot in dataSnapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)

                        if (user != null) {
                            if (user.email == userEmail) {
                                userId = user.id

                                val intent = Intent(this@ChatListActivity, EnterChatActivity::class.java)
                                intent.putExtra("userId", userId)
                                startActivity(intent)
                            }
                        }

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("ERROR","Error fetching user: ${databaseError.message}")
                }
            })
        }

    }

    /*
    private fun saveUserId(){
        val userEmail = intent.getStringExtra("email")

        var userId: String? = null

        databaseUsers.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (userSnapshot in dataSnapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)

                    if (user != null) {
                        if (user.email == userEmail) {
                            userId = user.id
                            Toast.makeText(this@ChatListActivity, "Id encontrado no loop: ${userId}", Toast.LENGTH_LONG).show()
                        }
                    }

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Erro ao buscar dados: ${databaseError.message}")
            }
        })

    }*/

}