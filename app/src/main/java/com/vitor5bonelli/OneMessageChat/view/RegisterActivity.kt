package com.vitor5bonelli.OneMessageChat.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.firestore
import com.vitor5bonelli.OneMessageChat.databinding.ActivityRegisterBinding
import com.vitor5bonelli.OneMessageChat.model.User

class RegisterActivity : AppCompatActivity() {

    private lateinit var arb: ActivityRegisterBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var databaseReference: DatabaseReference

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
                createUser(username, email, password)
            }
        }

    }

    private fun createUser(username: String, email: String, password: String){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val user = authResult.user
                val newUser = User(
                    id = user?.uid ?: "",
                    username = username,
                    email = email,
                    password = password
                )

                databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                databaseReference.child(newUser.id).setValue(newUser).addOnSuccessListener {
                    Log.i("UserDatabaseSuccess", "$newUser")
                    Toast.makeText(this, "User created successfully!", Toast.LENGTH_LONG).show()
                    switchToLoginView()

                }.addOnFailureListener {
                    Log.i("UserDatabaseFail", "$newUser")
                    Toast.makeText(this, "User creation failed!", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(this, "${exception.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun switchToLoginView(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        this.finish()
    }

}