package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wifood.R
import com.example.wifood.adapter.MenuGradeInfoAdapter
import com.example.wifood.databinding.ActivityEditFoodListBinding
import com.example.wifood.entity.Food

class EditFoodList : AppCompatActivity() {
    lateinit var binding : ActivityEditFoodListBinding
    lateinit var adapterMenuGradeInfo : MenuGradeInfoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 설정
        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // 뒤로가기 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정

        // 수정할 맛집에 대한 정보를 받아와 view 설정
        var food = intent.getParcelableExtra<Food>("food")
        val groupName = intent.getStringExtra("groupName")
        binding.groupName.text = groupName
        binding.foodName.text = food!!.name
        binding.foodAddress.text = food.address
        binding.memoText.text = food.memo
        binding.tasteGrade.text = food.myTasteGrade.toString()
        binding.kindnessGrade.text = food.myKindnessGrade.toString()
        binding.cleanGrade.text = food.myCleanGrade.toString()
        var s = ""
        for (i in 0 until food.menu.size) {
            s += food.menu[i].name
            if (i != food.menu.size - 1)
                s += ","
        }
        binding.foodMenu.text = s

        adapterMenuGradeInfo = MenuGradeInfoAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapterMenuGradeInfo
        adapterMenuGradeInfo.setMenuGradeListData(food.menuGrade)
        adapterMenuGradeInfo.notifyDataSetChanged()
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