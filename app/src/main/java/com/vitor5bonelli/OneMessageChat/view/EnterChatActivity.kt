package com.vitor5bonelli.OneMessageChat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vitor5bonelli.OneMessageChat.R
import com.vitor5bonelli.OneMessageChat.databinding.ActivityEnterChatBinding

class EnterChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEnterChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    override fun onResume() {
        super.onResume()

        binding.backBTN.setOnClickListener{
            finish()
        }
    }
}