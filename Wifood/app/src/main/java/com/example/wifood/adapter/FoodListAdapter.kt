package com.example.wifood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wifood.R
import com.example.wifood.entity.Food

class FoodListAdapter(private val context: Context): RecyclerView.Adapter<FoodListAdapter.FoodListViewHolder>() {
    private var foodList = mutableListOf<Food>()

    fun setListData(data:MutableList<Food>) {
        foodList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.food_list, parent, false)
        return FoodListViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodListViewHolder, position: Int) {
        val food: Food = foodList[position]
        holder.foodName.text = food.name
        holder.foodAddress.text = food.address
        holder.foodMemo.text = food.memo
        val grade : String = food.myTasteGrade.toString() + "/5"
        holder.foodGrade.text = grade
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    inner class FoodListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodName : TextView = itemView.findViewById(R.id.foodName)
        val foodAddress : TextView = itemView.findViewById(R.id.foodAddress)
        val foodMemo : TextView = itemView.findViewById(R.id.foodMemo)
        val foodGrade : TextView = itemView.findViewById(R.id.foodGrade)
    }
}