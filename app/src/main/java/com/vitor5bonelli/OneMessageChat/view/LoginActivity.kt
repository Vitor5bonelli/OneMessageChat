package com.vitor5bonelli.OneMessageChat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vitor5bonelli.OneMessageChat.R
import com.vitor5bonelli.OneMessageChat.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var alb: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alb = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(alb.root)
    }
}