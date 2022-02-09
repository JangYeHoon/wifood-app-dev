package com.example.wifood.activity

import android.content.Intent
import android.net.IpSecManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
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

        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // Drawer를 꺼낼 홈 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정
        supportActionBar?.title = "맛집리스트 추가"

        val gradeAdapter = ArrayAdapter.createFromResource(this, R.array.grade, android.R.layout.simple_spinner_item)
        binding.tasteGrade.adapter = gradeAdapter
        binding.tasteGrade.setSelection(3)
        binding.cleanGrade.adapter = gradeAdapter
        binding.cleanGrade.setSelection(3)
        binding.kindnessGrade.adapter = gradeAdapter
        binding.kindnessGrade.setSelection(3)

        binding.searchButton.setOnClickListener {
            val intent = Intent(this@AddFoodList, SearchPlace::class.java).apply {}
            requestActivity.launch(intent)
        }

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

    private val requestActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            searchResult = it.data?.getParcelableExtra("searchResult")!!
            binding.searchName.text = searchResult.name
            binding.searchBizName.text = searchResult.bizName
            binding.searchAddress.text = searchResult.fullAddress
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