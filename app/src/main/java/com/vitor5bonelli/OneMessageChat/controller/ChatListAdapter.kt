package com.vitor5bonelli.OneMessageChat.controller

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vitor5bonelli.OneMessageChat.R
import com.vitor5bonelli.OneMessageChat.databinding.ActivityChatCardBinding
import com.vitor5bonelli.OneMessageChat.databinding.ActivityChatListBinding
import com.vitor5bonelli.OneMessageChat.model.Chat

class ChatListAdapter(

    private val context: Context,
    private val chats : List<Chat>,

) : RecyclerView.Adapter<ChatListAdapter.ViewHolder>() {

    var onItemClick: ((Chat) -> Unit)? = null
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(chat: Chat) {
            val id = itemView.findViewById<TextView>(R.id.identificadorChatTV)
            val msg = itemView.findViewById<TextView>(R.id.mensagemTV)

            id.text = chat.id
            msg.text = chat.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.activity_chat_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = chats[position]
        holder.bind(chat)

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(chat)
        }
    }

    override fun getItemCount(): Int = chats.size
}
