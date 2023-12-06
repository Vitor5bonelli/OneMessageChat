package com.vitor5bonelli.OneMessageChat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.vitor5bonelli.OneMessageChat.R
import com.vitor5bonelli.OneMessageChat.databinding.ActivityCreateChatBinding
import com.vitor5bonelli.OneMessageChat.model.Chat
import com.vitor5bonelli.OneMessageChat.model.User

class CreateChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        binding.backBTN.setOnClickListener{
            finish()
        }

        binding.createBTN.setOnClickListener{
            /*
            val idChat = binding.idInputET.text.toString()
            val msgChat = binding.msgET.text.toString()

            val chat = Chat(id = idChat, message = msgChat)
            */

            //ler
            val firestore = Firebase.firestore
            firestore.collection("users")
                .get()
                .addOnSuccessListener {
                    it?.let { snapshot ->
                        for(documents in snapshot.documents){

                            Log.i("Products", "${documents.data}")
                        }
                    }
                }

            //registrar
            val user = User(id = "dHAHAHAH", username = "Vitor", email = "vetorbugos@gmail.com",
                password = "123456")

            val mappedUser = mapOf<String, Any>(
                "id" to user.id,
                "username" to user.username,
                "email" to user.email,
                "password" to user.password,
                "subscribedChats" to user.subscribedChats
            )

            firestore.collection("users")
                .add(mappedUser)
                .addOnSuccessListener {
                    it?.let {snapshot ->
                        Log.i("ChatsAdd", it.id)
                    }
                }
        }
    }
}