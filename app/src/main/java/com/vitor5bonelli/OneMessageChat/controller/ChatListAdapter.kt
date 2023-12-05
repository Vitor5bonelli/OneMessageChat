package com.vitor5bonelli.OneMessageChat.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vitor5bonelli.OneMessageChat.databinding.ActivityChatOnListBinding

class ChatListAdapter : AppCompatActivity() {

    private lateinit var binding: ActivityChatOnListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatOnListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}