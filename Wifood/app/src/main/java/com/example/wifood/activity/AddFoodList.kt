package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import com.example.wifood.R
import com.example.wifood.databinding.ActivityAddFoodListBinding
import com.example.wifood.entity.Search

class AddFoodList : AppCompatActivity() {
    lateinit var binding : ActivityAddFoodListBinding
    lateinit var searchResult: Search
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 설정
        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // 뒤로가기 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정
        supportActionBar?.title = "맛집리스트 추가"

        // 맛, 청결, 친절에 대해 선택할 수 있도록 값을 설정(1 ~ 5)
        val gradeAdapter = ArrayAdapter.createFromResource(this, R.array.grade, android.R.layout.simple_spinner_item)
        binding.tasteGrade.adapter = gradeAdapter
        binding.tasteGrade.setSelection(3)
        binding.cleanGrade.adapter = gradeAdapter
        binding.cleanGrade.setSelection(3)
        binding.kindnessGrade.adapter = gradeAdapter
        binding.kindnessGrade.setSelection(3)

        // 맛집 검색 SearchPlace Activity로 이동
        binding.searchButton.setOnClickListener {
            val intent = Intent(this@AddFoodList, SearchPlace::class.java).apply {}
            requestActivity.launch(intent)
        }

        // 맛집리스트를 추가할 수 있도록 name, memo, taste, clean, kindness 정보를 FoodList Activity로 넘겨줌
        // TODO("각각의 값을 넘겨주는게 아니라 여기서 group을 생성하고 넘겨주는게 좋나??")
        binding.saveBtn.setOnClickListener {
            val name = binding.searchName.text.toString()
            val memo = binding.memoText.text.toString()
            val tasteGrade:Double = binding.tasteGrade.selectedItem.toString().toDouble()
            val cleanGrade:Double = binding.cleanGrade.selectedItem.toString().toDouble()
            val kindnessGrade:Double = binding.kindnessGrade.selectedItem.toString().toDouble()
            if (name.isNotEmpty()) {
                val intent = Intent().apply {
                    putExtra("searchResult", searchResult)
                    putExtra("memo", memo)
                    putExtra("taste", tasteGrade)
                    putExtra("clean", cleanGrade)
                    putExtra("kindness", kindnessGrade)
                    putExtra("type", 0)
                }
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    // SearchPlace Activity로부터 받은 정보들을 이용해 name, bizname, address에 대한 text 설정
    private val requestActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            searchResult = it.data?.getParcelableExtra("searchResult")!!
            binding.searchName.text = searchResult.name
            binding.searchBizName.text = searchResult.bizName
            binding.searchAddress.text = searchResult.fullAddress
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