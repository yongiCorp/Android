package com.example.brandol

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemRVAdapter(private val itemList:List<Item>) : RecyclerView.Adapter<ItemRVAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val itemname : TextView = itemView.findViewById(R.id.item_wear_avartar_item_name_tv)
        val brandname : TextView = itemView.findViewById(R.id.item_wear_avartar_brand_name_tv)
        val image : ImageView = itemView.findViewById(R.id.item_wear_avartar_image_iv)
        val explain : TextView = itemView.findViewById(R.id.item_wear_avartar_explain_tv)
        val point : TextView = itemView.findViewById(R.id.item_wear_avartar_point_tv)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_wear_avartar, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemname.text = itemList.get(position).itemname
        holder.brandname.text = itemList.get(position).brandname
        holder.explain.text = itemList.get(position).explain
        holder.point.text = itemList.get(position).point
        holder.image.setImageResource(itemList.get(position).image)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}