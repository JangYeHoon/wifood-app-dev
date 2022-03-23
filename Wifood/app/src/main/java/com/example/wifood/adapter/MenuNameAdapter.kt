package com.example.wifood.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wifood.R
import com.example.wifood.entity.Group
import com.example.wifood.entity.Menu

class MenuNameAdapter(private val context: Context) :
    RecyclerView.Adapter<MenuNameAdapter.MenuViewHolder>() {
    private var menuList = mutableListOf<Menu>()

    fun setListData(data: MutableList<Menu>) {
        menuList = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuNameAdapter.MenuViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.menu_name, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuNameAdapter.MenuViewHolder, position: Int) {
        val menu: Menu = menuList[position]
        holder.menuName.text = menu.name
        holder.deleteButton.setOnClickListener {
            menuClickListener.onClick(it, position, menu)
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuName: TextView = itemView.findViewById(R.id.menuName)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteMenu)
    }

    interface MenuClickListener {
        fun onClick(view: View, position: Int, menu: Menu)
    }

    private lateinit var menuClickListener: MenuClickListener
    fun setMenuClickListener(menuClickListener: MenuClickListener) {
        this.menuClickListener = menuClickListener
    }
}