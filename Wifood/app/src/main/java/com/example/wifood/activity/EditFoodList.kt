package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.widget.Toolbar
import com.example.wifood.R
import com.example.wifood.databinding.ActivityEditFoodListBinding
import com.example.wifood.entity.Food

class EditFoodList : AppCompatActivity() {
    lateinit var binding : ActivityEditFoodListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 설정
        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // 뒤로가기 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정
        supportActionBar?.title = "맛집리스트 수정"

        // 수정할 맛집에 대한 정보를 받아와 view 설정
        var food = intent.getParcelableExtra<Food>("food")
        binding.foodName.text = food!!.name
        binding.foodAddress.text = food.address
        binding.memoText.setText(food.memo)

        val gradeAdapter = ArrayAdapter.createFromResource(this, R.array.grade, android.R.layout.simple_spinner_item)
        binding.tasteGrade.adapter = gradeAdapter
        binding.tasteGrade.setSelection(food.myTasteGrade.toInt() - 1)
        binding.cleanGrade.adapter = gradeAdapter
        binding.cleanGrade.setSelection(food.myCleanGrade.toInt() - 1)
        binding.kindnessGrade.adapter = gradeAdapter
        binding.kindnessGrade.setSelection(food.myKindnessGrade.toInt() - 1)

        // 변경된 정보를 받아와서 memo, taste, clean, kindness에 대한 정보를 수정하고 FoodListActivity에게 넘겨줌
        binding.saveBtn.setOnClickListener {
            food.memo = binding.memoText.text.toString()
            food.myTasteGrade = binding.tasteGrade.selectedItem.toString().toDouble()
            food.myCleanGrade = binding.cleanGrade.selectedItem.toString().toDouble()
            food.myKindnessGrade = binding.kindnessGrade.selectedItem.toString().toDouble()
            val intent = Intent().apply {
                putExtra("type", 1)
                putExtra("food", food)
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