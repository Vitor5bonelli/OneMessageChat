package com.vitor5bonelli.OneMessageChat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vitor5bonelli.OneMessageChat.R
import com.vitor5bonelli.OneMessageChat.databinding.ActivityCreateChatBinding

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
    }
}