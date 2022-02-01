package com.example.wifood.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wifood.R
import com.example.wifood.entity.Group

class WishGroupAdapter(private val context: Context): RecyclerView.Adapter<WishGroupAdapter.WishGroupViewHolder>() {
    private var wishGroupList = mutableListOf<Group>()

    fun setListData(data: MutableList<Group>) {
        wishGroupList = data
    }

    fun getGroupNameList() : MutableList<String> {
        var nameList = mutableListOf<String>()
        for (l in wishGroupList)
            nameList.add(l.name)
        return nameList
    }

    fun getGroupIdList() : MutableList<Int> {
        var nameList = mutableListOf<Int>()
        for (l in wishGroupList)
            nameList.add(l.id)
        return nameList
    }

    fun getGroupColorList() : MutableList<String> {
        var nameList = mutableListOf<String>()
        for (l in wishGroupList)
            nameList.add(l.color)
        return nameList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishGroupAdapter.WishGroupViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.group_list, parent, false)
        return WishGroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: WishGroupViewHolder, position: Int) {
        val foodGroup : Group = wishGroupList[position]
        holder.group_name.text = foodGroup.name
        holder.group_pin.setColorFilter(Color.parseColor(foodGroup.color))
        holder.group_edit.setOnClickListener {
            groupEditClickListener.onClick(it, position, foodGroup.id)
        }
    }

    override fun getItemCount(): Int {
        return wishGroupList.size
    }

    inner class WishGroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val group_name : TextView = itemView.findViewById(R.id.group_name)
        val group_pin : ImageView = itemView.findViewById(R.id.imageView)
        val group_edit : ImageButton = itemView.findViewById(R.id.editBtn)
    }

    interface GroupEditClickListener {
        fun onClick(view: View, position: Int, groupId: Int)
    }

    private lateinit var groupEditClickListener: GroupEditClickListener

    fun setGroupEditClickListener(groupEditClickListener: GroupEditClickListener) {
        this.groupEditClickListener = groupEditClickListener
    }
}