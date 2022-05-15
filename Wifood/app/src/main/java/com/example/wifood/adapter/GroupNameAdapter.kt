package com.example.wifood.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.wifood.R
import com.example.wifood.domain.entity.Group

class GroupNameAdapter(private val context: Context) :
    RecyclerView.Adapter<GroupNameAdapter.GroupNameViewHolder>() {
    private var groupList = mutableListOf<Group>()
    private var selectGroup = 0
    private var groupPosition: Int = 0

    fun setListData(data: MutableList<Group>) {
        groupList = data
    }

    fun setListDataClear() {
        groupList.clear()
    }

    fun setSelectGroupByGroupId(groupId: Int) {
        selectGroup = groupId
    }

    fun getGroupPosition(): Int {
        for (i in 0 until groupList.size) {
            if (groupList[i].id == selectGroup)
                groupPosition = i
        }
        return groupPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupNameViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.group_name, parent, false)
        return GroupNameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupNameAdapter.GroupNameViewHolder, position: Int) {
        val group: Group = groupList[position]
        holder.groupName.text = group.name
        if (group.id == selectGroup) {
            holder.groupName.background =
                ContextCompat.getDrawable(context, R.drawable.bg_rounding_box_check)
            holder.groupName.setTextColor(Color.WHITE)
        } else {
            holder.groupName.background =
                ContextCompat.getDrawable(context, R.drawable.bg_rounding_box)
            holder.groupName.setTextColor(Color.BLACK)
        }
        holder.groupName.setOnClickListener {
            groupClickListener.onClick(it, position, group)
        }
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    inner class GroupNameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val groupName: TextView = itemView.findViewById(R.id.textView_groupName)
    }

    interface GroupClickListener {
        fun onClick(view: View, position: Int, group: Group)
    }

    private lateinit var groupClickListener: GroupClickListener
    fun setGroupClickListener(groupClickListener: GroupClickListener) {
        this.groupClickListener = groupClickListener
    }
}