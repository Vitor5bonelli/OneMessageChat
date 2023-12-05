package com.vitor5bonelli.OneMessageChat.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

    override fun onResume() {
        super.onResume()

        //Go to register page
        alb.orRegisterLoginTV.setOnClickListener{
            switchToRegisterView()
        }

        //Try to login
        alb.LoginBTN.setOnClickListener{
            if ((alb.passwordLoginET.text.isBlank() || alb.passwordLoginET.text.isEmpty()) || (alb.emailLoginET.text.isBlank() || alb.emailLoginET.text.isEmpty()) ){
                Toast.makeText(this, "Fields are empty!", Toast.LENGTH_LONG).show()
            }
            else{
                signIn(alb.emailLoginET.text.toString(), alb.passwordLoginET.text.toString())
            }
        }
    }

    private fun signIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Login Error!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun switchToRegisterView(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        this.finish()
    }

}