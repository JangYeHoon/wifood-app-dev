package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wifood.R
import com.example.wifood.adapter.DeleteFoodGroupAdapter
import com.example.wifood.databinding.ActivityDeleteFoodGroupBinding
import com.example.wifood.entity.Group

class DeleteFoodGroup : AppCompatActivity() {
    lateinit var binding : ActivityDeleteFoodGroupBinding
    lateinit var deleteFoodGroupAdapter : DeleteFoodGroupAdapter
    var foodGroupList = mutableListOf<Group>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteFoodGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // Drawer를 꺼낼 홈 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정

        supportActionBar?.title = "그룹 삭제"

        val nameList = intent.getStringArrayListExtra("groupName")
        val idList = intent.getIntegerArrayListExtra("groupId")
        val colorList = intent.getStringArrayListExtra("groupColor")
        for (i in 0 until nameList!!.count())
            foodGroupList.add(Group(idList!![i], nameList[i], colorList!![i]))

        deleteFoodGroupAdapter = DeleteFoodGroupAdapter(this)
        binding.deleteRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.deleteRecyclerView.adapter = deleteFoodGroupAdapter
        binding.deleteRecyclerView.addItemDecoration(DividerItemDecoration(this, 1))
        deleteFoodGroupAdapter.setListData(foodGroupList)

        binding.deleteBtn.setOnClickListener {
            val intent = Intent().apply {
                putExtra("id", ArrayList(deleteFoodGroupAdapter.getDeleteFoodIdList()))
                putExtra("type", 2)
            }
            setResult(RESULT_OK, intent)
            finish()
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