package com.example.wifood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wifood.R
import com.example.wifood.entity.Food

class DeleteFoodListAdapter(private val context: Context) : RecyclerView.Adapter<DeleteFoodListAdapter.DeleteFoodListViewHolder>() {
    private var foodList = mutableListOf<Food>()

    fun setListData(data: MutableList<Food>) {
        foodList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeleteFoodListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.food_list_delete, parent, false)
        return DeleteFoodListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeleteFoodListViewHolder, position: Int) {
        val food : Food = foodList[position]
        holder.foodName.text = food.name
        holder.foodAddress.text = food.address
        holder.foodMemo.text = food.memo
        holder.itemView.setOnClickListener {
            deleteFoodListClickListener.onClick(it, position, food)
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
        fun onClick(view: View, position: Int, item: Food)
    }

    private lateinit var deleteFoodListClickListener: DeleteFoodListClickListener

    fun setDeleteFoodListClickListener(deleteFoodListClickListener: DeleteFoodListClickListener) {
        this.deleteFoodListClickListener = deleteFoodListClickListener
    }
}