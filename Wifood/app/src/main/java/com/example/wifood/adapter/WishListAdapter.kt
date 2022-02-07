package com.example.wifood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wifood.R
import com.example.wifood.entity.Wish

class WishListAdapter(private val context: Context): RecyclerView.Adapter<WishListAdapter.WishListViewHolder>() {
    private var wishList = mutableListOf<Wish>()

    fun setListData(data:MutableList<Wish>) {
        wishList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.wish_list, parent, false)
        return WishListViewHolder(view)
    }

    override fun onBindViewHolder(holder: WishListViewHolder, position: Int) {
        val wish: Wish = wishList[position]
        holder.wishName.text = wish.name
        holder.wishAddress.text = wish.address
        holder.wishMemo.text = wish.memo
        holder.itemView.setOnClickListener {
            wishListClickListener.onClick(it, position, wish)
        }
    }

    override fun getItemCount(): Int {
        return wishList.size
    }

    inner class WishListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wishName : TextView = itemView.findViewById(R.id.wishName)
        val wishAddress : TextView = itemView.findViewById(R.id.wishAddress)
        val wishMemo : TextView = itemView.findViewById(R.id.wishMemo)
    }

    interface WishListClickListener {
        fun onClick(view: View, position: Int, item: Wish)
    }

    private lateinit var wishListClickListener: WishListClickListener

    fun setWishListClickListener(wishListClickListener: WishListClickListener) {
        this.wishListClickListener = wishListClickListener
    }
}