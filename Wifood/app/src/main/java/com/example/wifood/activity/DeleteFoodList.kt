package com.example.wifood.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wifood.R
import com.example.wifood.adapter.DeleteFoodListAdapter
import com.example.wifood.databinding.ActivityDeleteFoodListBinding
import com.example.wifood.entity.Food

class DeleteFoodList : AppCompatActivity() {
    lateinit var binding : ActivityDeleteFoodListBinding
    lateinit var deleteFoodListAdapter: DeleteFoodListAdapter
    var selectedFoodList : SparseBooleanArray = SparseBooleanArray(0)
    var deleteFoodList : ArrayList<Int> = ArrayList(0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // Drawer를 꺼낼 홈 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정

        supportActionBar?.title = "맛집리스트 삭제"

        var foodlist = intent.getParcelableArrayListExtra<Food>("foodlist")
        deleteFoodListAdapter = DeleteFoodListAdapter(this)
        binding.deleteRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.deleteRecyclerView.adapter = deleteFoodListAdapter
        binding.deleteRecyclerView.addItemDecoration(DividerItemDecoration(this, 1))
        deleteFoodListAdapter.setListData(foodlist!!)

        binding.deleteBtn.setOnClickListener {
            if (deleteFoodList.size != 0) {
                val intent = Intent().apply {
                    putExtra("deleteIdList", deleteFoodList)
                    putExtra("type", 2)
                }
                setResult(RESULT_OK, intent)
            } else setResult(RESULT_CANCELED)
            finish()
        }

        deleteFoodListAdapter.setDeleteFoodListClickListener(object: DeleteFoodListAdapter.DeleteFoodListClickListener {
            override fun onClick(view: View, position: Int, item: Food) {
                if (selectedFoodList.get(position, false)) {
                    selectedFoodList.delete(position)
                    view.setBackgroundColor(Color.WHITE)
                    deleteFoodList.remove(item.id)
                } else {
                    selectedFoodList.put(position, true)
                    view.setBackgroundColor(Color.DKGRAY)
                    deleteFoodList.add(item.id)
                }
            }
        })
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