package com.example.brandol.adaptor.RV

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.brandol.collection.Chat
import com.example.brandol.ItemClickListener
import com.example.brandol.R

class ChattingRVAdapter(private var chatList: List<Chat>, private val myemail: String) :
    RecyclerView.Adapter<ChattingRVAdapter.ChatViewHodler>() {

    var itemClickListener: ItemClickListener? = null
    inner class ChatViewHodler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.name_tv)
        val userprofile: ImageView = itemView.findViewById(R.id.avatar_iv)
        val chattext: TextView = itemView.findViewById(R.id.chat_tv)

        //프로필 클릭 이벤트를 ChattingActivity에서 구현
        init {
            userprofile.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHodler {
        var itemView : View
        // 내 채팅이 아닐때
        if (viewType == 1) {
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.chatting_right_text_view, parent, false)
        }else{// 내 채팅일때
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.chatting_left_text_view_, parent, false)
        }
        return ChatViewHodler(itemView)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: ChatViewHodler, position: Int) {
        holder.chattext.text = chatList.get(position).text
        holder.userprofile.setImageResource(chatList.get(position).profile)
        holder.username.text = chatList.get(position).name
    }

    //이메일을 받아와서 내 이메일이면 1반환 아니면 2반환
    override fun getItemViewType(position: Int): Int {
        if (chatList.get(position).email.equals(myemail)) {
            return 1
        } else {
            return 2
        }
    }
}