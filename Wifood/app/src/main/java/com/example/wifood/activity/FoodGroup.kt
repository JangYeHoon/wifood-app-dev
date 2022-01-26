package com.example.wifood.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wifood.R
import com.example.wifood.adapter.FoodGroupAdapter
import com.example.wifood.entity.Group
import com.example.wifood.viewmodel.FoodGroupViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodGroup : AppCompatActivity() {
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

        val groupAddButton : FloatingActionButton = findViewById(R.id.groupAddButton)
        groupAddButton.setOnClickListener {
            val intent = Intent(this@FoodGroup, EditFoodGroup::class.java).apply {
                putExtra("type", "ADD")
            }
            requestActivity.launch(intent)
        }
    }

    private val requestActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            // create a group to add using the value received from EditFoodGroup Activity
            val group = Group(foodGroupAdapter.itemCount + 1, it.data?.getSerializableExtra("name") as String,
                it.data?.getSerializableExtra("color") as String)
            // type 0 : add, 1 : edit
            when(it.data?.getIntExtra("type", -1)) {
                0 -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        foodGroupViewModel.groupInsert(group)
                    }
                }
            }
        }
    }
}
