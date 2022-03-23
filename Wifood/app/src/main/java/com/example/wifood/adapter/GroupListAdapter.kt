package com.example.wifood.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wifood.R
import com.example.wifood.entity.Group
import java.util.*

class GroupListAdapter(private val context: Context) :
    RecyclerView.Adapter<GroupListAdapter.FoodGroupViewHolder>(),
    GroupItemTouchHelperCallback.OnItemMoveListener {
    private var groupList = mutableListOf<Group>()

    fun setListData(data: MutableList<Group>) {
        groupList = data
    }

    fun getListData(): ArrayList<Group> {
        val group: ArrayList<Group> = ArrayList(0)
        for (g in groupList)
            group.add(g)
        return group
    }

    fun setListDataClear() {
        groupList.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodGroupViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.group_list, parent, false)
        return FoodGroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupListAdapter.FoodGroupViewHolder, position: Int) {
        val foodGroup: Group = groupList[position]
        holder.groupName.text = foodGroup.name
        val t = "#" + foodGroup.theme
        holder.groupTheme.text = t
        holder.groupPin.setColorFilter(Color.parseColor(foodGroup.color))
        holder.groupEdit.setOnClickListener {
            groupEditClickListener.onClick(it, position, foodGroup)
        }
        holder.itemView.setOnClickListener {
            groupGoClickListener.onClick(it, position, foodGroup)
        }
        holder.groupMove.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.d("groupTouch", "ACTION_DOWN")
                    onStartDragListener.onStartDrag(holder)
                }
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    inner class FoodGroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val groupName: TextView = itemView.findViewById(R.id.group_name)
        val groupTheme: TextView = itemView.findViewById(R.id.group_theme)
        val groupPin: ImageView = itemView.findViewById(R.id.pinImage)
        val groupEdit: ImageButton = itemView.findViewById(R.id.editBtn)
        val groupMove: ImageView = itemView.findViewById(R.id.moveImage)
    }

    interface GroupEditClickListener {
        fun onClick(view: View, position: Int, group: Group)
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

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(groupList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    interface OnStartDragListener {
        fun onStartDrag(viewHolder: FoodGroupViewHolder)
    }

    private lateinit var onStartDragListener: OnStartDragListener
    fun setOnStartDragListener(onStartDragListener: OnStartDragListener) {
        this.onStartDragListener = onStartDragListener
    }
}