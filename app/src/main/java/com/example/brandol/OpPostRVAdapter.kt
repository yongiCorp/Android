package com.example.brandol

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OpPostRVAdapter(private val opPostList: List<OpPost>) :
    RecyclerView.Adapter<OpPostRVAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.item_usernick4_tv)
        val contents: TextView = itemView.findViewById(R.id.item_postcontent4_tv)
        val postname: TextView = itemView.findViewById(R.id.item_posttitle4_tv)
        val profile: ImageView = itemView.findViewById(R.id.item_profile4_iv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_board, parent, false)
        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.username.text = opPostList.get(position).username
        holder.postname.text = opPostList.get(position).posetname
        holder.contents.text = opPostList.get(position).contents
        holder.profile.setImageResource(opPostList.get(position).profile)
    }

    override fun getItemCount(): Int {
        return opPostList.size
    }

}