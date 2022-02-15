package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.example.wifood.R
import com.example.wifood.databinding.ActivityEditWishListBinding
import com.example.wifood.entity.Wish

class EditWishList : AppCompatActivity() {
    lateinit var binding : ActivityEditWishListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditWishListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 설정
        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // 뒤로가기 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정
        supportActionBar?.title = "위시리스트 수정"

        // 수정할 맛집에 대한 정보를 받아와 view 설정
        var wish = intent.getParcelableExtra<Wish>("wish")
        binding.wishName.text = wish?.name
        binding.wishAddress.text = wish?.address
        binding.memoText.setText(wish?.memo)

        // 변경된 정보를 받아와서 memo에 대한 정보를 수정하고 WishListActivity에게 넘겨줌
        binding.saveBtn.setOnClickListener {
            wish?.memo = binding.memoText.text.toString()
            val intent = Intent().apply {
                putExtra("type", 1)
                putExtra("wish", wish)
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