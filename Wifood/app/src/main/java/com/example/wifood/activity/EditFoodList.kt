package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.wifood.R
import com.example.wifood.adapter.MenuGradeInfoAdapter
import com.example.wifood.databinding.ActivityEditFoodListBinding
import com.example.wifood.entity.Food
import com.example.wifood.viewmodel.FoodListViewModel
import com.example.wifood.viewmodel.ImageStoreViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.Map

class EditFoodList : AppCompatActivity() {
    lateinit var binding : ActivityEditFoodListBinding
    lateinit var adapterMenuGradeInfo : MenuGradeInfoAdapter
    lateinit var food: Food
    lateinit var groupName: String
    lateinit var foodListViewModel: FoodListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 설정
        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // 뒤로가기 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정

        foodListViewModel = ViewModelProvider(this).get(FoodListViewModel::class.java)

        // 수정할 맛집에 대한 정보를 받아와 view 설정
        food = intent.getParcelableExtra<Food>("food")!!
        groupName = intent.getStringExtra("groupName")!!
        setActivityViewValue()
    }

    private fun downloadImage(idx: String, foodId: Int) {
        val storage:FirebaseStorage = FirebaseStorage.getInstance()
        val storageRef:StorageReference = storage.reference.child("$foodId/$idx.png")
        storageRef.downloadUrl.addOnCompleteListener {
            if (it.isSuccessful) {
                Glide.with(this@EditFoodList).load(it.result).into(binding.foodImage)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.popup_food, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 클릭한 툴바 메뉴 아이템의 id마다 다르게 실행하도록 설정
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.delete_menu -> {
                CoroutineScope(Dispatchers.IO).launch {
                    foodListViewModel.deleteFood(food.id)
                }
                finish()
            }
            R.id.edit_menu -> {
                val intent = Intent(this@EditFoodList, AddFoodList::class.java).apply {
                    putExtra("food", food)
                    putExtra("groupName", groupName)
                    putExtra("type", "edit")
                }
                requestActivity.launch(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val requestActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val editFood = it.data?.getParcelableExtra<Food>("food")
            val editGroupName = it.data?.getStringExtra("groupName")
            // type 0: add, 1: edit, 2: delete
            when(it.data?.getIntExtra("type", -1)) {
                1 -> {
                    // EditFoodListActivity에서 받은 수정된 food를 이용해 db 수정
                    CoroutineScope(Dispatchers.IO).launch {
                        foodListViewModel.updateFoodList(editFood!!)
                    }
                    food = editFood!!
                    groupName = editGroupName!!
                    setActivityViewValue()
                }
            }
        }
    }

    private fun setActivityViewValue() {
        binding.groupName.text = groupName
        binding.foodName.text = food!!.name
        binding.foodAddress.text = food.address
        binding.memoText.text = food.memo
        if (food.visited == 1) {
            binding.tasteGrade.text = food.myTasteGrade.toString()
            binding.kindnessGrade.text = food.myKindnessGrade.toString()
            binding.cleanGrade.text = food.myCleanGrade.toString()
        } else {
            binding.textMenu.visibility = View.GONE
            binding.recyclerView.visibility = View.GONE
            binding.foodGradeRow.visibility = View.GONE
        }
        var s = ""
        for (i in 0 until food.menu.size) {
            s += food.menu[i].name
            if (i != food.menu.size - 1)
                s += ","
        }
        binding.foodMenu.text = s

        adapterMenuGradeInfo = MenuGradeInfoAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapterMenuGradeInfo
        adapterMenuGradeInfo.setMenuGradeListData(food.menuGrade)
        adapterMenuGradeInfo.notifyDataSetChanged()

        binding.foodAddress.setOnClickListener {
            val intent = Intent(this@EditFoodList, Map::class.java)
            intent.putExtra("latitude", food.latitude)
            intent.putExtra("longitude", food.longitude)
            startActivity(intent)
        }

        if (food.imageUri.size > 0)
            downloadImage(food.imageUri[0], food.id)
    }
}