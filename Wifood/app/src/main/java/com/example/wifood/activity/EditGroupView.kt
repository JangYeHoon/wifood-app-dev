package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.wifood.R
import com.example.wifood.databinding.ActivityEditGroupBinding
import com.example.wifood.presentation.viewmodel.GroupViewModel

class EditGroupView : AppCompatActivity() {
    lateinit var binding: ActivityEditGroupBinding
    lateinit var inputMethodManager: InputMethodManager
    lateinit var groupViewModel: GroupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        // ImageView Array
        val pinArray = arrayOf(
            binding.pinImage1,
            binding.pinImage2,
            binding.pinImage3,
            binding.pinImage4,
            binding.pinImage5,
            binding.pinImage6,
            binding.pinImage7,
            binding.pinImage8,
            binding.pinImage9,
            binding.pinImage10
        )
        val type = intent.getStringExtra("type") as String

        // 툴바 설정
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // 뒤로가기 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정

        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)

        // Initialize the value in case of edit
        if (type == "EDIT") {
            supportActionBar?.title = "그룹 수정"
            groupViewModel.setGroupInstance(intent.getParcelableExtra("group")!!)
            binding.editTextGroupName.setText(groupViewModel.getGroupName())
            binding.editTextTheme.setText(groupViewModel.getGroupTheme())
            for (i in pinArray) {
                if (groupViewModel.getGroupPinColor() == i.contentDescription.toString()) {
                    i.scaleX = 2F
                    i.scaleY = 2F
                    break
                }
            }
        } else {
            groupViewModel.setGroupIdAndOrder(intent.getIntExtra("groupId", 1))
            supportActionBar?.title = "그룹 추가"
        }

        // click image size conversion
        for (selectPin in pinArray) {
            selectPin.setOnClickListener {
                inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.editTextGroupName.windowToken, 0)
                groupViewModel.setGroupPinColor(selectPin.contentDescription.toString())
                selectPin.scaleX = 2F
                selectPin.scaleY = 2F
                selectPin.requestLayout()
                for (pin in pinArray) {
                    if (selectPin != pin) {
                        pin.scaleX = 1F
                        pin.scaleY = 1F
                        pin.requestLayout()
                    }
                }
            }
        }

        binding.buttonSave.setOnClickListener {
            groupViewModel.setGroupName(binding.editTextGroupName.text.toString())
            groupViewModel.setGroupTheme(binding.editTextTheme.text.toString())
            if (!groupViewModel.isGroupEmpty()) {
                val intent = Intent().apply {
                    putExtra("group", groupViewModel.getGroupInstance())
                    if (type == "ADD") putExtra("type", 0)
                    else putExtra("type", 1)
                }
                setResult(RESULT_OK, intent)
                finish()
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
