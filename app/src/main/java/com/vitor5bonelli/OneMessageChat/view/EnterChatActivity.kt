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
import com.vitor5bonelli.OneMessageChat.R
import com.vitor5bonelli.OneMessageChat.databinding.ActivityEnterChatBinding
import com.vitor5bonelli.OneMessageChat.model.User

class EnterChatActivity : AppCompatActivity() {

    private var databaseChats: DatabaseReference = FirebaseDatabase.getInstance().getReference("Chats")
    private var databaseUsers: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")


    private lateinit var binding: ActivityEnterChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    override fun onResume() {
        super.onResume()

        val userId = intent.getStringExtra("userId")

        binding.sendBTN.setOnClickListener {
            val idChat = binding.idInputET.text.toString()

            databaseChats.child(idChat).get().addOnSuccessListener { chatSnapshot ->
                if (chatSnapshot.exists()) {
                    databaseUsers.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            var userFound = false

                            for (userSnapshot in dataSnapshot.children) {
                                val user = userSnapshot.getValue(User::class.java)

                                if (user != null && user.id == userId) {
                                    userFound = true

                                    if (idChat in user.subscribedChats) {
                                        Toast.makeText(this@EnterChatActivity, "You already subscribed to that chat!", Toast.LENGTH_LONG).show()
                                    } else {
                                        val updatedSubscribedChats = user.subscribedChats.toMutableList()
                                        updatedSubscribedChats.add(idChat)

                                        val updatedUser = User(
                                            id = user.id,
                                            username = user.username,
                                            email = user.email,
                                            password = user.password,
                                            subscribedChats = updatedSubscribedChats
                                        )

                                        databaseUsers.child(userId!!).setValue(updatedUser)
                                            .addOnSuccessListener {
                                                Toast.makeText(this@EnterChatActivity, "Subscribed successfully to $idChat!", Toast.LENGTH_LONG).show()
                                                finish()
                                            }
                                            .addOnFailureListener {
                                                Log.e("ERROR", "Failed to update user with subscribed chat")
                                            }
                                    }
                                    break
                                }
                            }

                            if (!userFound) {
                                Toast.makeText(this@EnterChatActivity, "User not found", Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.e("ERROR", "Error fetching user: ${databaseError.message}")
                        }
                    })
                } else {
                    Toast.makeText(this@EnterChatActivity, "Chat doesn't exist or invalid ID!", Toast.LENGTH_LONG).show()
                }
            }
        }


        binding.backBTN.setOnClickListener{
            finish()
        }
    }
}