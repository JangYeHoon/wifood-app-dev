package com.example.wifood.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
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

        // group add btn
        val groupAddButton : FloatingActionButton = findViewById(R.id.groupAddButton)
        groupAddButton.setOnClickListener {
            val intent = Intent(this@FoodGroup, EditFoodGroup::class.java).apply {
                putExtra("type", "ADD")
            }
            requestActivity.launch(intent)
        }

        // group del btn
        val groupDeleteButton : ImageButton = findViewById(R.id.groupDeleteButton)
        groupDeleteButton.setOnClickListener {
            val intent = Intent(this@FoodGroup, DeleteFoodGroup::class.java).apply {
                putExtra("groupName", ArrayList(foodGroupAdapter.getGroupNameList()))
                putExtra("groupId", ArrayList(foodGroupAdapter.getGroupIdList()))
                putExtra("groupColor", ArrayList(foodGroupAdapter.getGroupColorList()))
            }
            requestActivity.launch(intent)
        }
    }

    private val requestActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            // type 0 : add, 1 : edit, 2 : delete
            when(it.data?.getIntExtra("type", -1)) {
                0 -> {
                    val maxId = foodGroupAdapter.getGroupIdList().maxOrNull() ?: 0
                    // create a group to add using the value received from EditFoodGroup Activity
                    val group = Group(maxId + 1, it.data?.getSerializableExtra("name") as String,
                        it.data?.getSerializableExtra("color") as String)
                    CoroutineScope(Dispatchers.IO).launch {
                        foodGroupViewModel.groupInsert(group)
                    }
                }
                2 -> {
                    val groupId = it.data?.getIntegerArrayListExtra("id")
                    CoroutineScope(Dispatchers.IO).launch {
                        if (groupId != null)
                            foodGroupViewModel.groupDelete(groupId)
                    }
                    Log.d("groupDelete", groupId.toString())
                }
            }
        }
    }
}
