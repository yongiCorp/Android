package com.example.brandol.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brandol.R
import com.example.brandol.connection.RetrofitClient2
import com.example.brandol.searchCategory.CategoryItemClickListener


class BrandCategoryAdapter(private val itemClickListener: CategoryItemClickListener? = null) :
    RecyclerView.Adapter<BrandCategoryAdapter.BrandViewHolder>() {

    private val items = mutableListOf<RetrofitClient2.searchDetailBrandDto>()

    fun addItems(newItems: List<RetrofitClient2.searchDetailBrandDto>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_brand_categoty, parent, false)
        return BrandViewHolder(view)
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class BrandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val brandName: TextView = itemView.findViewById(R.id.name_tv)
        private val brandProfileImage: ImageView = itemView.findViewById(R.id.brand_logo_iv)
        private val brandBackgroundImage: ImageView =
            itemView.findViewById(R.id.brand_background_frame)
        private val brandDescription: TextView = itemView.findViewById(R.id.pr_tv)
        private val brandFans: TextView = itemView.findViewById(R.id.fan_tv)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val brandId = items[position].brandId
                itemClickListener?.onItemClick(brandId)
            }
        }

        fun bind(brandData: RetrofitClient2.searchDetailBrandDto) {
            brandName.text = brandData.brandName
            Glide.with(itemView.context).load(brandData.brandProfileImage).into(brandProfileImage)
            Glide.with(itemView.context).load(brandData.brandBackgroundImage)
                .into(brandBackgroundImage)
            brandDescription.text = brandData.brandDescription
            brandFans.text = brandData.brandFans.toString()
        }
    }
}
