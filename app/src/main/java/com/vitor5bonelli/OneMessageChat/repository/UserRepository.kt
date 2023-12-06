package com.vitor5bonelli.OneMessageChat.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vitor5bonelli.OneMessageChat.model.User

class UserRepository {
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    // Adicionar um novo usuário
    fun addUser(user: User) {
        val userId = databaseReference.push().key
        userId?.let {
            user.id = it
            databaseReference.child(it).setValue(user)
        }
    }

    // Obter detalhes de um usuário por ID
    fun getUserById(userId: String, callback: (User?) -> Unit) {
        databaseReference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                callback.invoke(user)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                callback.invoke(null)
            }
        })
    }

    // Atualizar detalhes de um usuário existente
    fun updateUser(user: User) {
        databaseReference.child(user.id).setValue(user)
    }

    // Deletar um usuário por ID
    fun deleteUser(userId: String) {
        databaseReference.child(userId).removeValue()
    }
}