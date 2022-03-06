package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wifood.R
import com.example.wifood.adapter.*
import com.example.wifood.databinding.ActivityAddFoodListBinding
import com.example.wifood.entity.*

class AddFoodList : AppCompatActivity() {
    lateinit var binding : ActivityAddFoodListBinding
    lateinit var searchResult: Search
    lateinit var adapterMenuName: MenuNameAdapter
    lateinit var adapterMenuGrade: MenuGradeAdapter
    lateinit var inputMethodManager: InputMethodManager
    // 별점 저장을 위한 변수
    var isVisited = false
    var insertFood: Food = Food()
    var menuList:ArrayList<Menu> = ArrayList(0)
    var listMenuGrade:ArrayList<MenuGrade> = ArrayList(0)

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
        binding.tasteGrade.setOnRatingBarChangeListener { ratingBar, rating, _ ->
            if (rating <= 0.5f) ratingBar.rating = 0.5f
            insertFood.myTasteGrade = rating.toDouble() }
        binding.cleanGrade.setOnRatingBarChangeListener { ratingBar, rating, _ ->
            if (rating < 0.5f) ratingBar.rating = 0.5f
            insertFood.myCleanGrade = rating.toDouble() }
        binding.kindnessGrade.setOnRatingBarChangeListener { ratingBar, rating, _ ->
            if (rating < 0.5f) ratingBar.rating = 0.5f
            insertFood.myKindnessGrade = rating.toDouble() }

        // 방문 여부 체크
        binding.isVisited.setOnCheckedChangeListener { _, onSwitch ->
            isVisited = onSwitch
            insertFood.visited = isVisited.toInt()
            if (onSwitch) {
                binding.tableLayout2.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.VISIBLE
                binding.menuTable.visibility = View.VISIBLE
                binding.recyclerMenuGrade.visibility = View.VISIBLE
                insertFood.myTasteGrade = binding.tasteGrade.rating.toDouble()
                insertFood.myCleanGrade = binding.cleanGrade.rating.toDouble()
                insertFood.myKindnessGrade = binding.kindnessGrade.rating.toDouble()
            } else {
                binding.tableLayout2.visibility = View.GONE
                binding.recyclerView.visibility = View.GONE
                binding.menuTable.visibility = View.GONE
                binding.recyclerMenuGrade.visibility = View.GONE
                insertFood.myTasteGrade = 0.0
                insertFood.myCleanGrade = 0.0
                insertFood.myKindnessGrade = 0.0
            }
        }

        adapterMenuName = MenuNameAdapter(this)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerView.adapter = adapterMenuName
        updateMenuListAdapter()
        binding.insertMenu.setOnClickListener {
            inputMenu()
        }
        binding.editMenu.setOnEditorActionListener { textView, i, keyEvent ->
            var handled = false
            if (i == EditorInfo.IME_ACTION_DONE) {
                inputMenu()
                handled = true
            }
            handled
        }
        adapterMenuName.setMenuClickListener(object: MenuNameAdapter.MenuClickListener{
            override fun onClick(view: View, position: Int, menu: Menu) {
                menuList.removeAt(position)
                updateMenuListAdapter()
            }
        })

        adapterMenuGrade = MenuGradeAdapter(this)
        binding.recyclerMenuGrade.layoutManager = LinearLayoutManager(this)
        binding.recyclerMenuGrade.adapter = adapterMenuGrade
        updateMenuGradeListAdapter()
        binding.addMenuGradeButton.setOnClickListener {
            val name:String = binding.editMenuName.text.toString()
            val price:Int = binding.editMenuPrice.text.toString().toInt()
            val grade:Double = binding.menuGrade.rating.toDouble()
            val memo:String = binding.editMenuMemo.text.toString()
            if (name != "") {
                listMenuGrade.add(MenuGrade(name, price, grade, memo))
                updateMenuGradeListAdapter()
                inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                binding.editMenuName.setText("")
                binding.editMenuMemo.setText("")
                binding.editMenuPrice.setText("")
                binding.menuGrade.rating = 0.5f
            }
        }

        val type = intent.getStringExtra("type")
        if (type  == "edit") {
            insertFood = intent.getParcelableExtra("food")!!
            binding.searchName.text = insertFood.name
            binding.searchAddress.text = insertFood.address
            menuList = insertFood.menu
            updateMenuListAdapter()
            if (insertFood.visited == 1) {
                listMenuGrade = insertFood.menuGrade
                updateMenuGradeListAdapter()
                binding.tasteGrade.rating = insertFood.myTasteGrade.toFloat()
                binding.kindnessGrade.rating = insertFood.myKindnessGrade.toFloat()
                binding.cleanGrade.rating = insertFood.myCleanGrade.toFloat()
                binding.isVisited.isChecked = true
                binding.tableLayout2.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.VISIBLE
                binding.menuTable.visibility = View.VISIBLE
                binding.recyclerMenuGrade.visibility = View.VISIBLE
                binding.memoText.setText(insertFood.memo)
            }
        } else if (type == "add") {
            insertFood.name = intent.getStringExtra("groupName").toString()
            insertFood.groupId = intent.getIntExtra("groupId", -1)
        }
        binding.groupName.text = insertFood.name

        // 맛집 검색 SearchPlace Activity로 이동
        if (type != "edit") {
            binding.searchName.setOnClickListener {
                val intent = Intent(this@AddFoodList, SearchPlace::class.java).apply {}
                requestActivity.launch(intent)
            }
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
            insertFood.menu = menuList
            insertFood.menuGrade = listMenuGrade
            if (insertFood.name != "None" && insertFood.groupId != -1) {
                val intent = Intent().apply {
                    putExtra("food", insertFood)
                    if (type == "edit") putExtra("type", 1)
                    else putExtra("type", 0)
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

    private fun inputMenu() {
        if (binding.editMenu.text.toString() != "") {
            menuList.add(Menu(binding.editMenu.text.toString()))
            updateMenuListAdapter()
            inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.editMenu.windowToken, 0)
            binding.editMenu.setText("")
        }
    }

    private fun updateMenuListAdapter() {
        adapterMenuName.setListData(menuList)
        adapterMenuName.notifyDataSetChanged()
    }

    private fun updateMenuGradeListAdapter() {
        adapterMenuGrade.setListData(listMenuGrade)
        adapterMenuGrade.notifyDataSetChanged()
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