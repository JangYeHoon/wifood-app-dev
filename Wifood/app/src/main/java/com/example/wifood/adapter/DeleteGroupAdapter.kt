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
import com.example.wifood.domain.entity.Group

class DeleteGroupAdapter(private val context: Context) :
    RecyclerView.Adapter<DeleteGroupAdapter.DeleteFoodGroupViewHolder>() {
    private var foodGroupList = mutableListOf<Group>()
    private var deleteFoodIdList = mutableListOf<Int>()     // 삭제할 food Id 저장하는 리스트
    fun setListData(data: MutableList<Group>) {
        foodGroupList = data
    }

    fun getDeleteFoodIdList(): MutableList<Int> {
        return deleteFoodIdList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeleteGroupAdapter.DeleteFoodGroupViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.group_delete, parent, false)
        return DeleteFoodGroupViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: DeleteGroupAdapter.DeleteFoodGroupViewHolder,
        position: Int
    ) {
        val foodGroup: Group = foodGroupList[position]
        holder.groupName.text = foodGroup.name
        holder.groupPin.setColorFilter(Color.parseColor(foodGroup.color))
        // chk 상태에 따라 삭제할 리스트에 저장
        holder.groupCheck.setOnClickListener {
            if (holder.groupCheck.isChecked)
                deleteFoodIdList.add(foodGroup.id)
            else
                deleteFoodIdList.remove(foodGroup.id)
        }
    }

    override fun getItemCount(): Int {
        return foodGroupList.size
    }

    inner class DeleteFoodGroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val groupName: TextView = itemView.findViewById(R.id.group_name)
        val groupPin: ImageView = itemView.findViewById(R.id.imageView)
        val groupCheck: CheckBox = itemView.findViewById(R.id.checkBox)
    }
}