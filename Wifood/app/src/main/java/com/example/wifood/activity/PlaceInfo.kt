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
import com.example.wifood.databinding.ActivityPlaceInfoBinding
import com.example.wifood.entity.Place
import com.example.wifood.viewmodel.PlaceViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.Map

class PlaceInfo : AppCompatActivity() {
    lateinit var binding: ActivityPlaceInfoBinding
    lateinit var adapterMenuGradeInfo: MenuGradeInfoAdapter
    lateinit var place: Place
    lateinit var groupName: String
    lateinit var placeViewModel: PlaceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 설정
        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // 뒤로가기 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정

        placeViewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)

        // 수정할 맛집에 대한 정보를 받아와 view 설정
        place = intent.getParcelableExtra<Place>("place")!!
        groupName = intent.getStringExtra("groupName")!!
        initActivityViewValue()
    }

    private fun initActivityViewValue() {
        binding.textViewGroupName.text = groupName
        binding.textViewPlaceName.text = place.name
        binding.textViewAddress.text = place.address
        binding.textViewPlaceMemo.text = place.memo
        if (place.visited == 1) {
            binding.textViewTasteGrade.text = place.myTasteGrade.toString()
            binding.textViewKindnessGrade.text = place.myKindnessGrade.toString()
            binding.textViewCleanGrade.text = place.myCleanGrade.toString()
            if (place.menuGrade.size <= 0)
                binding.textViewMenuGradeTitle.visibility = View.GONE
        } else {
            binding.textViewMenuGradeTitle.visibility = View.GONE
            binding.recyclerViewMenuGradeList.visibility = View.GONE
            binding.tableRowPlaceGrade.visibility = View.GONE
        }

        var s = ""
        for (i in 0 until place.menu.size) {
            s += place.menu[i].name
            if (i != place.menu.size - 1)
                s += ","
        }
        binding.textViewMenu.text = s

        adapterMenuGradeInfo = MenuGradeInfoAdapter(this)
        binding.recyclerViewMenuGradeList.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMenuGradeList.adapter = adapterMenuGradeInfo
        adapterMenuGradeInfo.setMenuGradeListData(place.menuGrade)
        adapterMenuGradeInfo.notifyDataSetChanged()

        binding.textViewAddress.setOnClickListener {
            val intent = Intent(this@PlaceInfo, Map::class.java)
            intent.putExtra("latitude", place.latitude)
            intent.putExtra("longitude", place.longitude)
            startActivity(intent)
        }

        if (place.imageUri.size > 0)
            getImageToDatabase(place.imageUri[0], place.id)
    }

    private fun getImageToDatabase(idx: String, foodId: Int) {
        val storage: FirebaseStorage = FirebaseStorage.getInstance()
        val storageRef: StorageReference = storage.reference.child("$foodId/$idx.png")
        storageRef.downloadUrl.addOnCompleteListener {
            if (it.isSuccessful) {
                Glide.with(this@PlaceInfo).load(it.result).into(binding.imageViewPlaceImage)
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
                    placeViewModel.deletePlace(place.id)
                }
                finish()
            }
            R.id.edit_menu -> {
                val intent = Intent(this@PlaceInfo, AddPlace::class.java).apply {
                    putExtra("place", place)
                    putExtra("groupName", groupName)
                    putExtra("type", "edit")
                }
                requestActivity.launch(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val requestActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val editPlace = it.data?.getParcelableExtra<Place>("place")
                val editGroupName = it.data?.getStringExtra("groupName")
                // group edit
                when (it.data?.getIntExtra("type", -1)) {
                    1 -> {
                        // EditPlaceListActivity에서 받은 수정된 place를 이용해 db 수정
                        CoroutineScope(Dispatchers.IO).launch {
                            placeViewModel.updatePlace(editPlace!!)
                        }
                        place = editPlace!!
                        groupName = editGroupName!!
                        initActivityViewValue()
                    }
                }
            }
        }
}