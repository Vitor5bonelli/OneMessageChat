package com.vitor5bonelli.OneMessageChat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vitor5bonelli.OneMessageChat.databinding.ActivityCreateChatBinding
import com.vitor5bonelli.OneMessageChat.model.Chat
import com.vitor5bonelli.OneMessageChat.model.User
import com.vitor5bonelli.OneMessageChat.repository.ChatRepository

class CreateChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateChatBinding
    private var chatRepo: ChatRepository = ChatRepository()
    private var databaseUsers: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        val userId = intent.getStringExtra("userId")

        binding.backBTN.setOnClickListener{
            finish()
        }

        binding.createBTN.setOnClickListener {
            val idChat = binding.idInputET.text.toString()
            val msgChat = binding.msgET.text.toString()

            val chat = Chat(id = idChat, message = msgChat)

            databaseUsers.addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (userSnapshot in dataSnapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)

                        if (user != null) {
                            if (user.id == userId) {

                                val updatedSubscribedChats = user.subscribedChats.toMutableList()
                                chat.id?.let { it1 -> updatedSubscribedChats.add(it1) }

                                val updatedUser = User(
                                    id = user.id,
                                    username = user.username,
                                    email = user.email,
                                    password = user.password,
                                    subscribedChats = updatedSubscribedChats
                                )

                                userId.let {
                                    databaseUsers.child(it).setValue(updatedUser).addOnSuccessListener {
                                        Log.i("Chat", "Created with sucess!")
                                    }.addOnFailureListener {
                                        Log.i("Chat", "Creation failed!")
                                    }
                                }

                                chatRepo.createChat(chat)

                                finish()

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
}