package com.example.wifood.adapter

import android.content.Context
import android.graphics.Color
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wifood.R
import com.example.wifood.entity.Group

class GroupAdapter(private val context: Context): RecyclerView.Adapter<GroupAdapter.FoodGroupViewHolder>() {
    private var groupList = mutableListOf<Group>()

    fun setListData(data:MutableList<Group>) {
        groupList = data
    }

    fun getGroupNameList() : MutableList<String> {
        var nameList = mutableListOf<String>()
        for (l in groupList)
            nameList.add(l.name)
        return nameList
    }

    fun getGroupIdList() : MutableList<Int> {
        var nameList = mutableListOf<Int>()
        for (l in groupList)
            nameList.add(l.id)
        return nameList
    }

    fun getGroupColorList() : MutableList<String> {
        var nameList = mutableListOf<String>()
        for (l in groupList)
            nameList.add(l.color)
        return nameList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodGroupViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.group_list, parent, false)
        return FoodGroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupAdapter.FoodGroupViewHolder, position: Int) {
        val foodGroup : Group = groupList[position]
        holder.group_name.text = foodGroup.name
        holder.group_pin.setColorFilter(Color.parseColor(foodGroup.color))
        holder.group_edit.setOnClickListener {
            groupEditClickListener.onClick(it, position, foodGroup.id)
        }
        // TODO{"버튼을 따로 만들지 말고 해당 리스트를 선택하면 넘어가게 할까"}
        holder.group_go.setOnClickListener {
            groupGoClickListener.onClick(it, position, foodGroup)
        }
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    inner class FoodGroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val group_name : TextView = itemView.findViewById(R.id.group_name)
        val group_pin : ImageView = itemView.findViewById(R.id.imageView)
        val group_edit : ImageButton = itemView.findViewById(R.id.editBtn)
        val group_go : ImageButton = itemView.findViewById(R.id.goBtn)
    }

    interface GroupEditClickListener {
        fun onClick(view: View, position: Int, groupId: Int)
    }

    private lateinit var groupEditClickListener: GroupEditClickListener

    fun setGroupEditClickListener(groupEditClickListener: GroupEditClickListener) {
        this.groupEditClickListener = groupEditClickListener
    }

    interface GroupGoClickListener {
        fun onClick(view: View, position: Int, group: Group)
    }

    private lateinit var groupGoClickListener: GroupGoClickListener

    fun setGroupGoClickListener(groupGoClickListener: GroupGoClickListener) {
        this.groupGoClickListener = groupGoClickListener
    }
}