package com.example.wifood.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wifood.R
import com.example.wifood.adapter.FoodGroupAdapter
import com.example.wifood.viewmodel.FoodGroupViewModel

class FoodGroupActivity : ComponentActivity() {
    private lateinit var foodGroupAdapter: FoodGroupAdapter
    lateinit var foodGroupViewModel : FoodGroupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_group)

        // Connecting RecyclerView and Adapter
        foodGroupViewModel = ViewModelProvider(this).get(FoodGroupViewModel::class.java)
        foodGroupAdapter = FoodGroupAdapter(this)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = foodGroupAdapter

        // Automatically change bindings when data changes
        foodGroupViewModel.foodGroupList.observe(this) {
            foodGroupAdapter.setListData(it)
            foodGroupAdapter.notifyDataSetChanged()
        }
    }
}