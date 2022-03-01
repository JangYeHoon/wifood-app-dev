package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import com.example.wifood.R
import com.example.wifood.adapter.GroupSelectBottom
import com.example.wifood.databinding.ActivityAddFoodListBinding
import com.example.wifood.entity.Food
import com.example.wifood.entity.Group
import com.example.wifood.entity.Search
import kotlinx.coroutines.selects.select

class AddFoodList : AppCompatActivity() {
    lateinit var binding : ActivityAddFoodListBinding
    lateinit var searchResult: Search

    // 별점 저장을 위한 변수
    var tasteGrade:Double = 0.0
    var cleanGrade:Double = 0.0
    var kindnessGrade:Double = 0.0
    var isVisited = false
    var insertFood: Food = Food()

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

        // 맛, 청결, 친절에 대한 별점 리스너 설정
        binding.tasteGrade.setOnRatingBarChangeListener { _, rating, _ -> insertFood.myTasteGrade = rating.toDouble() }
        binding.cleanGrade.setOnRatingBarChangeListener { _, rating, _ -> insertFood.myCleanGrade = rating.toDouble() }
        binding.kindnessGrade.setOnRatingBarChangeListener { _, rating, _ -> insertFood.myKindnessGrade = rating.toDouble() }

        // 방문 여부 체크
        binding.isVisited.setOnCheckedChangeListener { _, onSwitch ->
            isVisited = onSwitch
            insertFood.visited = isVisited.toInt()
            if (onSwitch) {
                binding.tableLayout2.visibility = View.VISIBLE
                insertFood.myTasteGrade = binding.tasteGrade.rating.toDouble()
                insertFood.myCleanGrade = binding.cleanGrade.rating.toDouble()
                insertFood.myKindnessGrade = binding.kindnessGrade.rating.toDouble()
            } else {
                binding.tableLayout2.visibility = View.GONE
                insertFood.myTasteGrade = 0.0
                insertFood.myCleanGrade = 0.0
                insertFood.myKindnessGrade = 0.0
            }
        }

        // 맛집 검색 SearchPlace Activity로 이동
        binding.searchName.setOnClickListener {
            val intent = Intent(this@AddFoodList, SearchPlace::class.java).apply{}
            requestActivity.launch(intent)
        }

        // 그룹 선택 다이얼로그로 이동
        binding.groupName.setOnClickListener {
            val bottomSheet = GroupSelectBottom()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        binding.nextGroup.setOnClickListener {
            val bottomSheet = GroupSelectBottom()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        // 맛집리스트를 추가할 수 있도록 설정된 food 정보를 FoodList Activity로 넘겨줌
        binding.saveBtn.setOnClickListener {
            insertFood.memo = binding.memoText.text.toString()
            if (insertFood.name != "None" && insertFood.groupId != -1) {
                val intent = Intent().apply {
                    putExtra("searchResult", searchResult)
                    putExtra("food", insertFood)
                    putExtra("type", 0)
                }
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun Boolean.toInt() = if (this) 1 else 0

    // SearchPlace Activity로부터 받은 정보들을 이용해 name, bizname, address에 대한 text 설정
    private val requestActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            searchResult = it.data?.getParcelableExtra("searchResult")!!
            binding.searchName.text = searchResult.name
            binding.searchAddress.text = searchResult.fullAddress
            insertFood.name = searchResult.name
            insertFood.address = searchResult.fullAddress
            insertFood.latitude = searchResult.latitude
            insertFood.longitude = searchResult.longitude
        }
    }

    fun receiveData(group:Group) {
        binding.groupName.text = group.name
        insertFood.groupId = group.id
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