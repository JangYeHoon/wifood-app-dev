package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import com.example.wifood.R
import com.example.wifood.databinding.ActivityAddWishListBinding
import com.example.wifood.entity.Search

class AddWishList : AppCompatActivity() {
    lateinit var binding : ActivityAddWishListBinding
    lateinit var searchResult: Search
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWishListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // Drawer를 꺼낼 홈 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정
        supportActionBar?.title = "위시리스트 추가"

        binding.searchButton.setOnClickListener {
            val intent = Intent(this@AddWishList, SearchPlace::class.java).apply {}
            requestActivity.launch(intent)
        }

        binding.saveBtn.setOnClickListener {
            val name = binding.searchName.text.toString()
            val memo = binding.memoText.text.toString()
            if (name.isNotEmpty()) {
                val intent = Intent().apply {
                    putExtra("searchResult", searchResult)
                    putExtra("memo", memo)
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