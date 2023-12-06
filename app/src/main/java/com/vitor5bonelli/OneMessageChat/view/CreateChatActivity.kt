package com.vitor5bonelli.OneMessageChat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.vitor5bonelli.OneMessageChat.databinding.ActivityCreateChatBinding
import com.vitor5bonelli.OneMessageChat.model.Chat
import com.vitor5bonelli.OneMessageChat.repository.ChatRepository

class CreateChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateChatBinding
    private var chatRepo: ChatRepository = ChatRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        val userId = intent.getStringExtra("userId")

        if (userId != null) {
            Toast.makeText(this, "Found user id! ${userId}", Toast.LENGTH_LONG).show()
        } else{
            Toast.makeText(this, "Fudeu", Toast.LENGTH_LONG).show()
        }

        binding.backBTN.setOnClickListener{
            finish()
        }

        binding.createBTN.setOnClickListener {
            val idChat = binding.idInputET.text.toString()
            val msgChat = binding.msgET.text.toString()

            val chat = Chat(id = idChat, message = msgChat)

            Log.i("ChatMontado", "${chat}")

            chatRepo.createChat(chat)
            finish()
        }
    }
}