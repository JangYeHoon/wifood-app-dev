package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wifood.R
import com.example.wifood.adapter.DeleteGroupAdapter
import com.example.wifood.databinding.ActivityDeleteGroupBinding
import com.example.wifood.entity.Group

class DeleteGroup : AppCompatActivity() {
    lateinit var binding: ActivityDeleteGroupBinding
    lateinit var deleteGroupAdapter: DeleteGroupAdapter
    var foodGroupList = mutableListOf<Group>()

    // TODO "해당 Activity 삭제"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 설정
        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // 뒤로가기 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정
        supportActionBar?.title = "그룹 삭제"

        // foodGroupList를 생성하기 위한 name, id, color ArrayList를 받아옴
        // TODO("FoodGroupActivity의 foodlist의 정보를 받아와서 출력으로 변경")
        val nameList = intent.getStringArrayListExtra("groupName")
        val idList = intent.getIntegerArrayListExtra("groupId")
        val colorList = intent.getStringArrayListExtra("groupColor")
        for (i in 0 until nameList!!.count())
            foodGroupList.add(Group(idList!![i], nameList[i], colorList!![i], "", 0))

        // 삭제할 foodGroup을 선택할 수 있도록 foodgrouplist에 대한 recyclerView 설정
        deleteGroupAdapter = DeleteGroupAdapter(this)
        binding.deleteRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.deleteRecyclerView.adapter = deleteGroupAdapter
        binding.deleteRecyclerView.addItemDecoration(DividerItemDecoration(this, 1))
        deleteGroupAdapter.setListData(foodGroupList)

        // 선택된 삭제할 foodGroup들에 대한 id를 FoodGroupActivity로 넘겨줌
        binding.deleteBtn.setOnClickListener {
            val intent = Intent().apply {
                putExtra("id", ArrayList(deleteGroupAdapter.getDeleteFoodIdList()))
                putExtra("type", 2)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 툴바 메뉴에 뒤로가기 버튼
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}