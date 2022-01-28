package com.example.wifood.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wifood.R
import com.example.wifood.entity.Group

class FoodGroupAdapter(private val context: Context): RecyclerView.Adapter<FoodGroupAdapter.FoodGroupViewHolder>() {
    private var foodGroupList = mutableListOf<Group>()

    fun setListData(data:MutableList<Group>) {
        foodGroupList = data
    }

    fun getGroupNameList() : MutableList<String> {
        var nameList = mutableListOf<String>()
        for (l in foodGroupList)
            nameList.add(l.name)
        return nameList
    }

    fun getGroupIdList() : MutableList<Int> {
        var nameList = mutableListOf<Int>()
        for (l in foodGroupList)
            nameList.add(l.id)
        return nameList
    }

    fun getGroupColorList() : MutableList<String> {
        var nameList = mutableListOf<String>()
        for (l in foodGroupList)
            nameList.add(l.color)
        return nameList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodGroupAdapter.FoodGroupViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.food_group_list, parent, false)
        return FoodGroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodGroupAdapter.FoodGroupViewHolder, position: Int) {
        val foodGroup : Group = foodGroupList[position]
        holder.group_name.text = foodGroup.name
        holder.group_pin.setColorFilter(Color.parseColor(foodGroup.color))
    }

    override fun getItemCount(): Int {
        return foodGroupList.size
    }

    inner class FoodGroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val group_name : TextView = itemView.findViewById(R.id.group_name)
        val group_pin : ImageView = itemView.findViewById(R.id.imageView)
    }
}