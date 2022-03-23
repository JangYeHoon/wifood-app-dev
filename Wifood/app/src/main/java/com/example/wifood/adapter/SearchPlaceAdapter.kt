package com.example.wifood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wifood.R
import com.example.wifood.entity.Search

class SearchPlaceAdapter(private val context: Context) :
    RecyclerView.Adapter<SearchPlaceAdapter.SearchPlaceViewHolder>() {

    private var searchPlaceList = mutableListOf<Search>()

    fun setListData(data: MutableList<Search>) {
        searchPlaceList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchPlaceViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.search_result, parent, false)
        return SearchPlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchPlaceViewHolder, position: Int) {
        val search: Search = searchPlaceList[position]
        holder.searchName.text = search.name
        holder.searchAddress.text = search.fullAddress
        holder.searchBizName.text = search.bizName
        holder.itemView.setOnClickListener {
            searchResultClickListener.onClick(it, position, search)
        }
    }

    override fun getItemCount(): Int {
        return searchPlaceList.size
    }

    inner class SearchPlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val searchName: TextView = itemView.findViewById(R.id.searchName)
        val searchAddress: TextView = itemView.findViewById(R.id.searchAddress)
        val searchBizName: TextView = itemView.findViewById(R.id.searchBizName)
    }

    interface SearchResultClickListener {
        fun onClick(view: View, position: Int, item: Search)
    }

    private lateinit var searchResultClickListener: SearchResultClickListener

    fun setSearchResultClickListener(searchResultClickListener: SearchResultClickListener) {
        this.searchResultClickListener = searchResultClickListener
    }
}