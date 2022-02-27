package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.Toolbar
import com.example.wifood.R
import com.example.wifood.databinding.ActivityEditGroupBinding
import com.example.wifood.entity.Food
import com.example.wifood.entity.Group

class EditGroup : AppCompatActivity() {
    lateinit var binding : ActivityEditGroupBinding
    var pinColor : String = ""
    lateinit var inputMethodManager: InputMethodManager
    lateinit var group:Group

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 설정
        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // 뒤로가기 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정

        // ImageView Array
        val pinArray = arrayOf(binding.pinImage1, binding.pinImage2, binding.pinImage3, binding.pinImage4, binding.pinImage5, binding.pinImage6,
            binding.pinImage7, binding.pinImage8, binding.pinImage9, binding.pinImage10)
        val type = intent.getStringExtra("type") as String
        val id = intent.getIntExtra("groupId", 0)

        // Initialize the value in case of edit
        if (type == "EDIT") {
            supportActionBar?.title = "그룹 수정"
            group = intent.getParcelableExtra("group")!!
            binding.groupTitle.setText(group.name)
            binding.themeTitle.setText(group.theme)
            for (i in pinArray) {
                if (group.color == i.contentDescription.toString()) {
                    pinColor = group.color
                    i.scaleX = 2F
                    i.scaleY = 2F
                    break
                }
            }
        } else supportActionBar?.title = "그룹 추가"

        // click image size conversion
        for (i in pinArray) {
            i.setOnClickListener {
                inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.groupTitle.windowToken, 0)
                pinColor = i.contentDescription.toString()
                i.scaleX = 2F
                i.scaleY = 2F
                i.requestLayout()
                for (j in pinArray) {
                    if (i != j) {
                        j.scaleX = 1F
                        j.scaleY = 1F
                        j.requestLayout()
                    }
                }
            }
        }

        // TODO("로직 수정")
        binding.saveBtn.setOnClickListener {
            val title = binding.groupTitle.text.toString()
            val theme = binding.themeTitle.text.toString()
            group.name = title
            group.theme = theme
            group.color = pinColor
            // when adding a group
            if (type == "ADD") {
                if (title.isNotEmpty() && pinColor.isNotEmpty()) {
                    val intent = Intent().apply {
                        putExtra("name", title)
                        putExtra("color", pinColor)
                        putExtra("theme", theme)
                        putExtra("type", 0)
                    }
                    setResult(RESULT_OK, intent)
                    finish()
                }
            // when editing a group
            } else {
                if (title.isNotEmpty() && pinColor.isNotEmpty()) {
                    val intent = Intent().apply {
                        putExtra("group", group)
                        putExtra("type", 1)
                    }
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
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
