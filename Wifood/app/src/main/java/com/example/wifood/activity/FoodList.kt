package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wifood.R
import com.example.wifood.adapter.FoodListAdapter
import com.example.wifood.databinding.ActivityFoodListBinding
import com.example.wifood.entity.Food
import com.example.wifood.entity.Search
import com.example.wifood.entity.Wish
import com.example.wifood.viewmodel.FoodListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        // foodlist add btn
        binding.foodListAddButton.setOnClickListener {
            val intent = Intent(this@FoodList, AddFoodList::class.java)
            requestActivity.launch(intent)
        }
    }

    private val requestActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            when(it.data?.getIntExtra("type", -1)) {
                0 -> {
                    val searchResult = it.data?.getParcelableExtra<Search>("searchResult")
                    val tasteGrade = it.data?.getDoubleExtra("taste", 1.0)
                    val cleanGrade = it.data?.getDoubleExtra("clean", 1.0)
                    val kindnessGrade = it.data?.getDoubleExtra("kindness", 1.0)
                    val memo = it.data?.getStringExtra("memo")
                    val food = Food(foodListViewModel.getFoodListMaxId() + 1, searchResult!!.name, memo!!,
                        searchResult.fullAddress, searchResult.latitude, searchResult.longitude,
                        tasteGrade!!, cleanGrade!!, kindnessGrade!!)
                    CoroutineScope(Dispatchers.IO).launch {
                        foodListViewModel.insertFoodList(food)
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 클릭한 툴바 메뉴 아이템의 id마다 다르게 실행하도록 설정
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}