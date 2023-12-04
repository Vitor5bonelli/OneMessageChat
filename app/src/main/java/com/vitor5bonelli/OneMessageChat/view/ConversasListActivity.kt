package com.vitor5bonelli.OneMessageChat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vitor5bonelli.OneMessageChat.R
import com.vitor5bonelli.OneMessageChat.databinding.ActivityConversasListBinding

class ConversasListActivity : AppCompatActivity() {

    private lateinit var aclb: ActivityConversasListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        aclb = ActivityConversasListBinding.inflate(layoutInflater)
        setContentView(aclb.root)
    }
}