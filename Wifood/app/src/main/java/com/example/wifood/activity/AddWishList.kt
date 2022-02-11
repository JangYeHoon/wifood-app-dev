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

        // 툴바 설정
        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // 뒤로가기 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정
        supportActionBar?.title = "위시리스트 추가"

        // 맛집 검색 SearchPlace Activity로 이동
        binding.searchButton.setOnClickListener {
            val intent = Intent(this@AddWishList, SearchPlace::class.java).apply {}
            requestActivity.launch(intent)
        }

        // 위시리스트를 추가할 수 있도록 name, memo 정보를 WishList Activity로 넘겨줌
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
            TODO("여기서 wish를 생성하고 넘겨주는게 좋은가")
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