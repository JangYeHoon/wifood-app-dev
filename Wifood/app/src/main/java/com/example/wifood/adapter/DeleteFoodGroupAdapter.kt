package com.example.wifood.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wifood.R
import com.example.wifood.entity.Group

class DeleteFoodGroupAdapter(private val context: Context): RecyclerView.Adapter<DeleteFoodGroupAdapter.DeleteFoodGroupViewHolder>() {
    private var foodGroupList = mutableListOf<Group>()
    private var deleteFoodIdList = mutableListOf<Int>()
    fun setListData(data:MutableList<Group>) {
        foodGroupList = data
    }

    fun getDeleteFoodIdList() : MutableList<Int>{
        return deleteFoodIdList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeleteFoodGroupAdapter.DeleteFoodGroupViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.food_group_delete, parent, false)
        return DeleteFoodGroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeleteFoodGroupAdapter.DeleteFoodGroupViewHolder, position: Int) {
        val foodGroup : Group = foodGroupList[position]
        holder.group_name.text = foodGroup.name
        holder.group_pin.setColorFilter(Color.parseColor(foodGroup.color))
        holder.group_check.setOnClickListener{
            if (holder.group_check.isChecked)
                deleteFoodIdList.add(foodGroup.id)
            else
                deleteFoodIdList.remove(foodGroup.id)
        }
    }

    override fun getItemCount(): Int {
        return foodGroupList.size
    }

    inner class DeleteFoodGroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val group_name : TextView = itemView.findViewById(R.id.group_name)
        val group_pin : ImageView = itemView.findViewById(R.id.imageView)
        val group_check : CheckBox = itemView.findViewById(R.id.checkBox)
    }
}