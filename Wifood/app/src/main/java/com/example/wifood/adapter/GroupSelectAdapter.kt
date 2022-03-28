package com.example.wifood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wifood.R
import com.example.wifood.entity.Group

class GroupSelectAdapter(private val context: Context) :
    RecyclerView.Adapter<GroupSelectAdapter.GroupSelectViewHolder>() {
    private var groupList = mutableListOf<Group>()

    fun setListData(data: MutableList<Group>) {
        groupList = data
    }

    fun setListDataClear() {
        groupList.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupSelectViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.group_select, parent, false)
        return GroupSelectViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupSelectAdapter.GroupSelectViewHolder, position: Int) {
        val group: Group = groupList[position]
        holder.groupName.text = group.name
        holder.groupName.setOnClickListener {
            groupClickListener.onClick(it, position, group)
        }
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    inner class GroupSelectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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