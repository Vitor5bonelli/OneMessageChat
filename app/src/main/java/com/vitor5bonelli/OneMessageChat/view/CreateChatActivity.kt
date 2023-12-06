package com.vitor5bonelli.OneMessageChat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vitor5bonelli.OneMessageChat.R
import com.vitor5bonelli.OneMessageChat.databinding.ActivityCreateChatBinding
import com.vitor5bonelli.OneMessageChat.model.Chat

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

            val idChat = binding.idInputET.text.toString()
            val msgChat = binding.msgET.text.toString()

            val chat = Chat(id = idChat, message = msgChat)
            
        }
    }
}