package com.example.wifood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wifood.R
import com.example.wifood.domain.entity.MenuGrade

class MenuGradeAdapter(private val context: Context) :
    RecyclerView.Adapter<MenuGradeAdapter.MenuGradeViewHolder>() {
    private var listMenuGrade = mutableListOf<MenuGrade>()

    fun setListData(data: MutableList<MenuGrade>) {
        listMenuGrade = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuGradeAdapter.MenuGradeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.menu_grade_list, parent, false)
        return MenuGradeViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuGradeAdapter.MenuGradeViewHolder, position: Int) {
        val menu: MenuGrade = listMenuGrade[position]
        holder.name.text = menu.name
        holder.price.text = menu.price.toString()
        holder.grade.rating = menu.grade.toFloat()
        holder.grade.setIsIndicator(true)
        holder.memo.text = menu.memo
    }

    override fun getItemCount(): Int {
        return listMenuGrade.size
    }

    inner class MenuGradeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.textView_menuName)
        val price: TextView = itemView.findViewById(R.id.textView_menuPrice)
        val grade: RatingBar = itemView.findViewById(R.id.ratingBar_menuGrade)
        val memo: TextView = itemView.findViewById(R.id.textView_menuMemo)
    }
}