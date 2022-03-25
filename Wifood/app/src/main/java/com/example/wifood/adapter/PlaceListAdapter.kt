package com.example.wifood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wifood.R
import com.example.wifood.entity.Place
import kotlin.math.round

class PlaceListAdapter(private val context: Context) :
    RecyclerView.Adapter<PlaceListAdapter.PlaceListViewHolder>() {
    private var placeList = mutableListOf<Place>()

    fun setPlaceListData(data: MutableList<Place>) {
        placeList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.place_list, parent, false)
        return PlaceListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceListViewHolder, position: Int) {
        val place: Place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
        holder.placeMemo.text = place.memo
        if (place.visited == 1) {
            val gradeScore = (place.myTasteGrade + place.myCleanGrade + place.myKindnessGrade) / 3
            val grade = "${round(gradeScore * 10) / 10}/5"  // 출력하는 평점은 taste, clean, kind의 평균
            holder.myGrade.text = grade
        } else {
            holder.myGrade.visibility = View.INVISIBLE
        }
        holder.itemView.setOnClickListener {
            placeListClickListener.onClick(it, position, place)
        }
        holder.popupMenu.setOnClickListener {
            popupClickListener.onClick(it, position, place)
        }
    }

    override fun getItemCount(): Int {
        return placeList.size
    }

    inner class PlaceListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val placeName: TextView = itemView.findViewById(R.id.textView_placeName)
        val placeAddress: TextView = itemView.findViewById(R.id.textView_placeAddress)
        val placeMemo: TextView = itemView.findViewById(R.id.textView_placeMemo)
        val myGrade: TextView = itemView.findViewById(R.id.textView_placeGrade)
        val popupMenu: ImageButton = itemView.findViewById(R.id.imageButton_popupMenu)
    }

    interface PlaceListClickListener {
        fun onClick(view: View, position: Int, item: Place)
    }

    private lateinit var popupClickListener: PlaceListClickListener
    private lateinit var placeListClickListener: PlaceListClickListener

    fun setPlaceListClickListener(foodListClickListener: PlaceListClickListener) {
        this.placeListClickListener = foodListClickListener
    }

    fun setPopupButtonClickListener(popupClickListener: PlaceListClickListener) {
        this.popupClickListener = popupClickListener
    }
}