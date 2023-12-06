package com.vitor5bonelli.OneMessageChat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

        val userId = intent.getStringExtra("userId")

        if (userId != null) {
            Toast.makeText(this, "Found user id! ${userId}", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "Fudeu", Toast.LENGTH_LONG).show()
        }

        binding.backBTN.setOnClickListener{
            finish()
        }
    }
}