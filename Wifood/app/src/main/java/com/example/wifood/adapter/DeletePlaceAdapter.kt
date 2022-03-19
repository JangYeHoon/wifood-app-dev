package com.example.wifood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wifood.R
import com.example.wifood.entity.Place

class DeletePlaceAdapter(private val context: Context) : RecyclerView.Adapter<DeletePlaceAdapter.DeleteFoodListViewHolder>() {
    private var foodList = mutableListOf<Place>()

    fun setListData(data: MutableList<Place>) {
        foodList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeleteFoodListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.food_list_delete, parent, false)
        return DeleteFoodListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeleteFoodListViewHolder, position: Int) {
        val place : Place = foodList[position]
        holder.foodName.text = place.name
        holder.foodAddress.text = place.address
        holder.foodMemo.text = place.memo
        holder.itemView.setOnClickListener {
            deleteFoodListClickListener.onClick(it, position, place)
        }
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    inner class DeleteFoodListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodName : TextView = itemView.findViewById(R.id.foodName)
        val foodAddress : TextView = itemView.findViewById(R.id.foodAddress)
        val foodMemo : TextView = itemView.findViewById(R.id.memoText)
    }

    interface DeleteFoodListClickListener {
        fun onClick(view: View, position: Int, item: Place)
    }

    private lateinit var deleteFoodListClickListener: DeleteFoodListClickListener

    fun setDeleteFoodListClickListener(deleteFoodListClickListener: DeleteFoodListClickListener) {
        this.deleteFoodListClickListener = deleteFoodListClickListener
    }
}