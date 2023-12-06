package com.vitor5bonelli.OneMessageChat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            finish()
        }

        binding.backBTN.setOnClickListener{
            finish()
        }
    }

}