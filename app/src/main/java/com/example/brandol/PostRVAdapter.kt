package com.example.brandol

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostRVAdapter(private val postList: List<Post>) :
    RecyclerView.Adapter<PostRVAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.item_post_name_tv)
        val contents: TextView = itemView.findViewById(R.id.item_post_contents_tv)
        val noticeBoard: TextView = itemView.findViewById(R.id.item_post_notice_board_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.name.text = postList.get(position).name
        holder.contents.text = postList.get(position).contents
        holder.noticeBoard.text = postList.get(position).noticeBoard
    }

    override fun getItemCount(): Int {
        return postList.size
    }

}