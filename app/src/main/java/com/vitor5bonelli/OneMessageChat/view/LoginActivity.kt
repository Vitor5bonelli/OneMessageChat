package com.vitor5bonelli.OneMessageChat.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vitor5bonelli.OneMessageChat.databinding.ActivityLoginBinding
import java.util.Optional
import java.util.UUID

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

            val email: String = alb.emailLoginET.text.toString()
            val password: String = alb.passwordLoginET.text.toString()
            Log.i("email", email)
            Log.i("senha", password)

            if (email.isEmpty() || password.isEmpty() ){
                Toast.makeText(this, "Fields are empty!", Toast.LENGTH_LONG).show()
            }
            else{
                signIn(email, password)
            }
        }
    }

    private fun signIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.isSuccessful){

                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                switchToChatListView(email)

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

    private fun switchToChatListView(email: String){
        val intent = Intent(this, ChatListActivity::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
        this.finish()
    }

}