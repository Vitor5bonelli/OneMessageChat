package com.vitor5bonelli.OneMessageChat.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.vitor5bonelli.OneMessageChat.R
import com.vitor5bonelli.OneMessageChat.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var arb: ActivityRegisterBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arb = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(arb.root)

        auth = Firebase.auth
    }

    override fun onResume() {
        super.onResume()

        //Go to login page
        arb.orLoginTV.setOnClickListener{
            switchToLoginView()
        }

        //Try to register
        arb.registerBTN.setOnClickListener{
            val username: String = arb.usernameET.text.toString()
            val email: String = arb.emailET.text.toString()
            val password: String = arb.passwordET.text.toString()

            if(username.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Fields are empty!", Toast.LENGTH_LONG).show()
            }
            else{
                createUser(email, password)
            }
        }

    }

    private fun createUser(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this, "User creation successful!", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "User creation failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun switchToLoginView(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        this.finish()

    }
}