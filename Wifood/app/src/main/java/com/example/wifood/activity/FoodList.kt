package com.example.wifood.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wifood.R
import com.example.wifood.adapter.FoodListAdapter
import com.example.wifood.databinding.ActivityFoodListBinding
import com.example.wifood.viewmodel.FoodListViewModel

class FoodList : AppCompatActivity() {
    lateinit var binding : ActivityFoodListBinding
    private lateinit var foodListAdapter: FoodListAdapter
    lateinit var foodListViewModel: FoodListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // Drawer를 꺼낼 홈 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정

        val groupId = intent.getIntExtra("groupId", 0)

        foodListViewModel = ViewModelProvider(this, FoodListViewModel.Factory(groupId)).get(FoodListViewModel::class.java)
        foodListAdapter = FoodListAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = foodListAdapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, 1))

        foodListViewModel.foodList.observe(this) {
            foodListAdapter.setListData(it)
            foodListAdapter.notifyDataSetChanged()
        }
    }
}