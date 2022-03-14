package com.example.wifood.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.wifood.R
import com.example.wifood.entity.Group

class GroupNameAdapter(private val context: Context): RecyclerView.Adapter<GroupNameAdapter.FoodGroupViewHolder>() {
    private var groupList = mutableListOf<Group>()
    private var selectGroup = 0
    private var groupPosition: Int = 0

    fun setListData(data:MutableList<Group>) {
        groupList = data
    }

    fun setListDataClear() {
        groupList.clear()
    }

    fun setSelectGroup(groupId: Int) {
        selectGroup = groupId
    }

    fun getGroupPosition(): Int {
        for (i in 0 until groupList.size) {
            if (groupList[i].id == selectGroup)
                groupPosition = i
        }
        return groupPosition
    }

    fun getGroupIdList() : MutableList<Int> {
        var nameList = mutableListOf<Int>()
        for (l in groupList)
            nameList.add(l.id)
        return nameList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodGroupViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.group_name, parent, false)
        return FoodGroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupNameAdapter.FoodGroupViewHolder, position: Int) {
        val group : Group = groupList[position]
        holder.group_name.text = group.name
        if (group.id == selectGroup) {
            holder.group_name.background = ContextCompat.getDrawable(context, R.drawable.bg_rounding_box_check)
            holder.group_name.setTextColor(Color.WHITE)
        } else {
            holder.group_name.background = ContextCompat.getDrawable(context, R.drawable.bg_rounding_box)
            holder.group_name.setTextColor(Color.BLACK)
        }
        holder.group_name.setOnClickListener {
            groupClickListener.onClick(it, position, group)
        }
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    inner class FoodGroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val group_name : TextView = itemView.findViewById(R.id.group_name)
    }

    interface GroupClickListener {
        fun onClick(view: View, position: Int, group: Group)
    }
    private lateinit var groupClickListener: GroupClickListener
    fun setGroupClickListener(groupClickListener: GroupClickListener) {
        this.groupClickListener = groupClickListener
    }
}