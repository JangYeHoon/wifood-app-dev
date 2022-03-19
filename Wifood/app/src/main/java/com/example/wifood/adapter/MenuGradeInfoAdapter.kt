package com.example.wifood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wifood.R
import com.example.wifood.entity.MenuGrade

class MenuGradeInfoAdapter(private val context: Context): RecyclerView.Adapter<MenuGradeInfoAdapter.MenuGradeInfoViewHolder>() {
    private var listMenuGrade = mutableListOf<MenuGrade>()

    fun setMenuGradeListData(data:MutableList<MenuGrade>) {
        listMenuGrade = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuGradeInfoAdapter.MenuGradeInfoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.menu_grade_info, parent, false)
        return MenuGradeInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuGradeInfoAdapter.MenuGradeInfoViewHolder, position: Int) {
        val menuGrade: MenuGrade = listMenuGrade[position]
        val menuInfo = menuGrade.name + " - " + menuGrade.price + "원"
        holder.menuName.text = menuInfo
        holder.menuGrade.rating = menuGrade.grade.toFloat()
        holder.menuMemo.text = menuGrade.memo
    }

    override fun getItemCount(): Int {
        return listMenuGrade.size
    }

    inner class MenuGradeInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuName: TextView = itemView.findViewById(R.id.menuName)
        val menuGrade: RatingBar = itemView.findViewById(R.id.menuGrade)
        val menuMemo: TextView = itemView.findViewById(R.id.menuMemo)
    }
}