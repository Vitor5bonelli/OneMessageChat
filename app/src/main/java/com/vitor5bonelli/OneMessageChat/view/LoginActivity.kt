package com.vitor5bonelli.OneMessageChat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.vitor5bonelli.OneMessageChat.R
import com.vitor5bonelli.OneMessageChat.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var alb: ActivityLoginBinding

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alb = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(alb.root)

        auth = Firebase.auth
    }

    private fun createUserWithEmailAndPassword(){

    }
}