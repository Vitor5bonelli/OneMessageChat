package com.vitor5bonelli.OneMessageChat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vitor5bonelli.OneMessageChat.databinding.ActivityChatListBinding

class ChatListActivity : AppCompatActivity() {

    private lateinit var aclb: ActivityChatListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        aclb = ActivityChatListBinding.inflate(layoutInflater)
        setContentView(aclb.root)
    }

}