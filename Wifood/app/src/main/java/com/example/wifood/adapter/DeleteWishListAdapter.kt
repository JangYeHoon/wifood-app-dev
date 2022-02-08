package com.example.wifood.adapter

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wifood.R
import com.example.wifood.entity.Wish

class DeleteWishListAdapter(private val context: Context) : RecyclerView.Adapter<DeleteWishListAdapter.DeleteWishListViewHolder>() {
    private var wishList = mutableListOf<Wish>()

    fun setListData(data:MutableList<Wish>) {
        wishList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeleteWishListAdapter.DeleteWishListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.wish_list_delete, parent, false)
        return DeleteWishListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeleteWishListAdapter.DeleteWishListViewHolder, position: Int) {
        val wish : Wish = wishList[position]
        holder.wishName.text = wish.name
        holder.wishAddress.text = wish.address
        holder.wishMemo.text = wish.memo
        holder.itemView.setOnClickListener {
            deleteWishListClickListener.onClick(it, position, wish)
        }
    }

    override fun getItemCount(): Int {
        return wishList.size
    }

    inner class DeleteWishListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wishName : TextView = itemView.findViewById(R.id.wishName)
        val wishAddress : TextView = itemView.findViewById(R.id.wishAddress)
        val wishMemo : TextView = itemView.findViewById(R.id.memoText)
    }

    interface DeleteWishListClickListener {
        fun onClick(view: View, position: Int, item: Wish)
    }

    private lateinit var deleteWishListClickListener: DeleteWishListClickListener

    fun setDeleteWishListClickListener(deleteWishListClickListener: DeleteWishListClickListener) {
        this.deleteWishListClickListener = deleteWishListClickListener
    }
}