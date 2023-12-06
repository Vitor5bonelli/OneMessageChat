package com.vitor5bonelli.OneMessageChat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.vitor5bonelli.OneMessageChat.R
import com.vitor5bonelli.OneMessageChat.databinding.ActivityEditChatBinding
import com.vitor5bonelli.OneMessageChat.model.Chat

class EditChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val chat = intent.getParcelableExtra<Chat>("chat")

        if (chat != null) {
            binding.chatIdTV.text = chat.id
            binding.messageTV.text = chat.message
        }

        binding.editBTN.setOnClickListener{
            val chatId = binding.chatIdTV.text.toString()
            val chatMsg = binding.msgEditET.text.toString()
            updateChat(chatId, chatMsg)
            finish()
        }

        binding.backBTN.setOnClickListener{
            finish()
        }
    }

    private fun updateChat(chatId: String, chatMsg: String){
        val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Chats")
        val chatData = mapOf<String, String>(
            "message" to chatMsg
        )

        database.child(chatId).updateChildren(chatData).addOnSuccessListener {
            Toast.makeText(this, "New message sent!", Toast.LENGTH_LONG).show()
        }.addOnFailureListener{
            Toast.makeText(this, "Unable to update", Toast.LENGTH_LONG).show()

        }

    }

}