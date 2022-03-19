package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.example.wifood.entity.Place
import com.example.wifood.viewmodel.PlaceViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.Map

class PlaceInfo : AppCompatActivity() {
    lateinit var binding : ActivityEditFoodListBinding
    lateinit var adapterMenuGradeInfo : MenuGradeInfoAdapter
    lateinit var place: Place
    lateinit var groupName: String
    lateinit var placeViewModel: PlaceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 설정
        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // 뒤로가기 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정

        placeViewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)

        // 수정할 맛집에 대한 정보를 받아와 view 설정
        place = intent.getParcelableExtra<Place>("food")!!
        groupName = intent.getStringExtra("groupName")!!
        setActivityViewValue()
    }

    private fun downloadImage(idx: String, foodId: Int) {
        val storage:FirebaseStorage = FirebaseStorage.getInstance()
        val storageRef:StorageReference = storage.reference.child("$foodId/$idx.png")
        storageRef.downloadUrl.addOnCompleteListener {
            if (it.isSuccessful) {
                Glide.with(this@PlaceInfo).load(it.result).into(binding.foodImage)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.popup_place_menu, menu)
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
                    placeViewModel.deleteFood(place.id)
                }
                finish()
            }
            R.id.edit_menu -> {
                // TODO "food data class에 group name까지 넣어서 한번에 처리
                val intent = Intent(this@PlaceInfo, AddPlace::class.java).apply {
                    putExtra("food", place)
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
            // TODO "food data class에 group name까지 넣어서 한번에 처리
            val editFood = it.data?.getParcelableExtra<Place>("food")
            val editGroupName = it.data?.getStringExtra("groupName")
            // type 0: add, 1: edit, 2: delete
            when(it.data?.getIntExtra("type", -1)) {
                1 -> {
                    // EditFoodListActivity에서 받은 수정된 food를 이용해 db 수정
                    CoroutineScope(Dispatchers.IO).launch {
                        placeViewModel.updateFoodList(editFood!!)
                    }
                    place = editFood!!
                    groupName = editGroupName!!
                    setActivityViewValue()
                }
            }
        }
    }

    private fun setActivityViewValue() {
        binding.groupName.text = groupName
        binding.foodName.text = place!!.name
        binding.foodAddress.text = place.address
        binding.memoText.text = place.memo
        if (place.visited == 1) {
            binding.tasteGrade.text = place.myTasteGrade.toString()
            binding.kindnessGrade.text = place.myKindnessGrade.toString()
            binding.cleanGrade.text = place.myCleanGrade.toString()
            if (place.menuGrade.size <= 0)
                binding.textMenu.visibility = View.GONE
        } else {
            binding.textMenu.visibility = View.GONE
            binding.recyclerView.visibility = View.GONE
            binding.foodGradeRow.visibility = View.GONE
        }
        // TODO "string join하는 방법 찾아서 처리하기"
        var s = ""
        for (i in 0 until place.menu.size) {
            s += place.menu[i].name
            if (i != place.menu.size - 1)
                s += ","
        }
        binding.foodMenu.text = s

        adapterMenuGradeInfo = MenuGradeInfoAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapterMenuGradeInfo
        adapterMenuGradeInfo.setMenuGradeListData(place.menuGrade)
        adapterMenuGradeInfo.notifyDataSetChanged()

        binding.foodAddress.setOnClickListener {
            val intent = Intent(this@PlaceInfo, Map::class.java)
            intent.putExtra("latitude", place.latitude)
            intent.putExtra("longitude", place.longitude)
            startActivity(intent)
        }

        if (place.imageUri.size > 0)
            downloadImage(place.imageUri[0], place.id)
    }
}