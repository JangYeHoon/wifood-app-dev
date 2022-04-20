package com.example.wifood.activity

import android.content.Intent
import android.graphics.Color
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
import com.example.wifood.entity.Group
import com.example.wifood.entity.Place
import com.example.wifood.viewmodel.GroupViewModel
import com.example.wifood.viewmodel.PlaceViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.Map

class PlaceInfoView : AppCompatActivity(), OnMapReadyCallback {
    lateinit var binding: ActivityPlaceInfoBinding
    lateinit var adapterMenuGradeInfo: MenuGradeInfoAdapter
    lateinit var placeViewModel: PlaceViewModel
    lateinit var groupViewModel: GroupViewModel

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
        placeViewModel.initPlace(intent.getParcelableExtra("place")!!)

        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)

        groupViewModel.getGroupTaskToFireBase(placeViewModel.getPlaceGroupId())
            .addOnSuccessListener {
                it.getValue(Group::class.java)
                    ?.let { group -> groupViewModel.setGroupInstance(group) }
                initActivityViewValue()
            }
    }

    private fun initActivityViewValue() {
        binding.textViewGroupName.text = groupViewModel.getGroupName()
        binding.textViewPlaceName.text = placeViewModel.getPlaceName()
        binding.textViewAddress.text = placeViewModel.getPlaceAddress()
        binding.textViewPlaceMemo.text = placeViewModel.getPlaceMemo()
        if (placeViewModel.isVisited()) {
            binding.textViewTasteGrade.text = placeViewModel.getTasteGrade().toString()
            binding.textViewKindnessGrade.text = placeViewModel.getKindnessGrade().toString()
            binding.textViewCleanGrade.text = placeViewModel.getCleanGrade().toString()
            if (placeViewModel.isMenuGradeEmpty())
                binding.textViewMenuGradeTitle.visibility = View.GONE
        } else {
            binding.textViewMenuGradeTitle.visibility = View.GONE
            binding.recyclerViewMenuGradeList.visibility = View.GONE
            binding.tableRowPlaceGrade.visibility = View.GONE
        }

        binding.textViewMenu.text = placeViewModel.getMenuListToString()

        adapterMenuGradeInfo = MenuGradeInfoAdapter(this)
        binding.recyclerViewMenuGradeList.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMenuGradeList.adapter = adapterMenuGradeInfo
        adapterMenuGradeInfo.setMenuGradeListData(placeViewModel.getMenuGradeList())
        adapterMenuGradeInfo.notifyDataSetChanged()

        binding.textViewAddress.setOnClickListener {
            val intent = Intent(this@PlaceInfoView, Map::class.java)
            intent.putExtra("latitude", placeViewModel.getPlaceLatitude())
            intent.putExtra("longitude", placeViewModel.getPlaceLongitude())
            startActivity(intent)
        }

        if (!placeViewModel.isImageEmpty())
            getImageToDatabase(placeViewModel.getImageName(), placeViewModel.getPlaceId())

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.fragment_placeMap) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.fragment_placeMap, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    private fun getImageToDatabase(idx: String, foodId: Int) {
        val storage: FirebaseStorage = FirebaseStorage.getInstance()
        val storageRef: StorageReference = storage.reference.child("$foodId/$idx.png")
        storageRef.downloadUrl.addOnCompleteListener {
            if (it.isSuccessful) {
                Glide.with(this@PlaceInfoView).load(it.result).into(binding.imageViewPlaceImage)
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
                    placeViewModel.deletePlace()
                }
                finish()
            }
            R.id.edit_menu -> {
                val intent = Intent(this@PlaceInfoView, EditPlaceView::class.java).apply {
                    putExtra("place", placeViewModel.getPlaceInstance())
                    putExtra("groupName", groupViewModel.getGroupName())
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
                        placeViewModel.initPlace(editPlace!!)
                        groupViewModel.setGroupName(editGroupName!!)
                        initActivityViewValue()
                    }
                }
            }
        }

    override fun onMapReady(p0: NaverMap) {
        val naverMap: NaverMap = p0
        val cameraUpdate = CameraUpdate.scrollTo(
            LatLng(
                placeViewModel.getPlaceLatitude(),
                placeViewModel.getPlaceLongitude()
            )
        )
        naverMap.moveCamera(cameraUpdate)
        val cameraZoomUpdate = CameraUpdate.zoomTo(16.0)
            .animate(CameraAnimation.Easing, 1000)
        naverMap.moveCamera(cameraZoomUpdate)

        val marker = Marker()
        marker.position = LatLng(
            placeViewModel.getPlaceLatitude(),
            placeViewModel.getPlaceLongitude()
        )
        marker.iconTintColor = Color.parseColor(groupViewModel.getGroupPinColor())
        marker.map = naverMap
    }
}