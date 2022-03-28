package com.example.wifood.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.annotation.UiThread
import com.example.wifood.R

import com.naver.maps.map.util.FusedLocationSource
import androidx.core.view.GravityCompat

import androidx.drawerlayout.widget.DrawerLayout
import android.widget.Toast
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wifood.adapter.FoodListAdapter
import com.example.wifood.adapter.GroupNameAdapter
import com.example.wifood.databinding.ActivityMapBinding
import com.example.wifood.entity.Place
import com.example.wifood.entity.Group
import com.example.wifood.viewmodel.GroupViewModel
import com.example.wifood.viewmodel.PlaceViewModel
import com.google.android.material.navigation.NavigationView
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons
import kotlinx.android.synthetic.main.*;
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.menu_nav_header.view.*

class Map : AppCompatActivity(), OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding : ActivityMapBinding

    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    private lateinit var navigationView: NavigationView
    private lateinit var drawerLayout: DrawerLayout

    // 선택된 음식점으로 카메라 이동을 위한 좌표
    private var placeLatitude = 0.0
    private var placeLongitude = 0.0

    // FoodGroup , FoodList 선언
    lateinit var foodGroupViewModel: FoodGroupViewModel
    lateinit var foodListViewModel: FoodListViewModel
    private lateinit var foodListAdapter: FoodListAdapter
    private lateinit var groupListAdapter: GroupNameAdapter
    var arrMarkerList = mutableListOf<Marker>()
    var groupId: Int = 0
    var mColor: String = ""
    
    //var arrFoodGroup = mutableListOf<Group>()   // FoodGroup의 가변리스트
    var arrFoodList = mutableListOf<Food>()     // FoodList의 가변리스트

    // Firebase 연결
    /*
    private val db = Firebase.database;
    private val dbRootPath = "FoodGroup";
    private val dbRef = db.getReference(dbRootPath);
    */

    // onCreate()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_map)

        val userEmail = intent.getStringExtra("UserEmail").toString()
        Toast.makeText(applicationContext, userEmail, Toast.LENGTH_LONG).show()

        // 툴바를 Activity의 앱바로 적용
        val toolbar:Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // Drawer를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)  // 툴바 三버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정

        // 네비게이션 Drawer 생성
        drawerLayout = findViewById(R.id.main_drawer_layout)

        // 네비게이션 Drawer에 있는 화면의 Event를 처리하기 위해 생성
        navigationView = findViewById(R.id.main_navigationView)
        navigationView.setNavigationItemSelectedListener(this)

        // 데이터베이스 접근을 위한 food group id정보 받아옴
        groupId = intent.getIntExtra("groupId", 0)
        // 툴바 하단 그룹리스트 버튼 생성
        foodGroupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
        groupListAdapter = GroupNameAdapter(this)
        binding.includeMapLayout.filterBtn.layoutManager = LinearLayoutManager(this).also {
            it.orientation = LinearLayoutManager.HORIZONTAL
        }
        val spaceDecoration = VerticalSpaceItemDecoration(10)
        binding.includeMapLayout.filterBtn.addItemDecoration(spaceDecoration)
        binding.includeMapLayout.filterBtn.adapter = groupListAdapter

        foodGroupViewModel.foodGroupList.observe(this) {
            updateGroupAdapterList()
            binding.includeMapLayout.allBtn.background = ContextCompat.getDrawable(this@Map, R.drawable.bg_rounding_box)
            binding.includeMapLayout.allBtn.setTextColor(Color.BLACK)
            binding.includeMapLayout.filterBtn.smoothScrollToPosition(groupListAdapter.getGroupPosition())
        }

        // FoodGroup 버튼
        groupListAdapter.setGroupClickListener(object: GroupNameAdapter.GroupClickListener {
            override fun onClick(view: View, position: Int, group: Group) {
                groupId = group.id
                mColor = group.color
                updateGroupAdapterList()
                binding.includeMapLayout.allBtn.background = ContextCompat.getDrawable(this@Map, R.drawable.bg_rounding_box)
                binding.includeMapLayout.allBtn.setTextColor(Color.BLACK)
                getFoodListData(groupId)
                drawFoodMarker(arrFoodList)
            }
        })

        // 전체 버튼
        binding.includeMapLayout.allBtn.setOnClickListener {
            groupId = -1
            //groupListAdapter.setSelectGroup(-1)
            updateGroupAdapterList()
            binding.includeMapLayout.allBtn.background = ContextCompat.getDrawable(this@Map, R.drawable.bg_rounding_box_check)
            binding.includeMapLayout.allBtn.setTextColor(Color.WHITE)
            getFoodListData(groupId)
            drawFoodMarker(arrFoodList)
        }

        // 하단 메뉴 Event
        // button event : go to map
        binding.btnGoMainMap.setOnClickListener{
            Toast.makeText(applicationContext, "현재 메뉴 입니다", Toast.LENGTH_LONG).show()
        }

        // button event : go to my list
        btnGoMyList.setOnClickListener {
            val intent = Intent(this, GroupList::class.java)
            intent.putExtra("UserEmail",userEmail)
            startActivity(intent)
        }

        // button event : go to my information
        binding.btnGoMyPage.setOnClickListener {
            val intent = Intent(this, MyPage::class.java)
            intent.putExtra("UserEmail", userEmail)
            startActivity(intent)
        }

        // 지도 객체 생성
        val fm = supportFragmentManager // 다른 Fragment내에 배치할 경우 childFragmentManager로
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        // getMapAsync()를 호출하여 비동기로 onMapReady 콜백 메서드 호출
        // onMapReady에서 NaverMap 객체를 받음
        mapFragment.getMapAsync(this)

        // 위치를 반환하는 구현체인 FusedLocationSource 생성
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        placeLatitude = intent.getDoubleExtra("latitude", 0.0)
        placeLongitude = intent.getDoubleExtra("longitude", 0.0)
    }

    // 위치정보 사용자 권한 설정
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            // 권한 거부됨
            if (!locationSource.isActivated) {
                naverMap.locationTrackingMode = LocationTrackingMode.None   // 권한 거부되면 위치추적 하지 않음.
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    // 호출 시 Map에 뿌려주기
    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        // naverMap 객체 받아서 naverMap 객체에 위치 소스 지정
        this.naverMap = naverMap
        naverMap.locationSource = locationSource

        // 맛집리스트에서 선택한 음식점에 대한 해당 좌표로 카메라 이동하고 마커 생성
        if (placeLatitude != 0.0 && placeLongitude != 0.0) {
            val cameraUpdate = CameraUpdate.scrollTo(LatLng(placeLatitude, placeLongitude))
            naverMap.moveCamera(cameraUpdate)
            val cameraZoomUpdate = CameraUpdate.zoomTo(16.0)
                .animate(CameraAnimation.Easing, 1000)
            naverMap.moveCamera(cameraZoomUpdate)
        }
        // 현재위치 표시
        else {
            val uiSettings = naverMap.uiSettings
            uiSettings.isLocationButtonEnabled = true
            naverMap.locationTrackingMode =
                LocationTrackingMode.Face   // 위치추적 활성화, 현위치 오버레이, 카메라 좌표, 베어링이 사용자의 위치 및 방향에 따라 움직임
        }


        // FireBase 데이터 읽기 후 Marker 표시
        /*
        dbRef.addValueEventListener(object :ValueEventListener {
            // 데이터 변경 시
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.key.isNullOrEmpty())
                    return

                // FoodGroup의 모든 FoodList 위, 경도 Marker 표시
                for (i in 0 until arrFoodGroup.size) {
                    val wishGroupName: String = arrFoodGroup[i].name
                    val wishListData = snapshot.child(arrFoodGroup[i].id.toString()).child("foodlist")

                    for (wish in wishListData.children) {
                        val wishName: String = wish.child("name").value as String
                        val wishLatitude: Double = wish.child("latitude").value as Double
                        val wishLongitude: Double = wish.child("longitude").value as Double

                        // Marker 표시 코드 (Naver-API)
                        val wishMarker = Marker()
                        wishMarker.position = LatLng(wishLatitude, wishLongitude)
                        wishMarker.map = naverMap
                        wishMarker.icon = MarkerIcons.BLUE
                        wishMarker.width = Marker.SIZE_AUTO
                        wishMarker.height = Marker.SIZE_AUTO
                        wishMarker.captionText = wishGroupName + '_' + wishName
                    }
                }
            }

            // 데이터 삭제 시
            override fun onCancelled(error:DatabaseError) {
                println("loadItem:onCancelled : ${error.toException()}")
            }
        })
        */
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

    // 검색, 공유 Event 버튼 툴바에 집어 넣기
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    // 툴바의 버튼 클릭 시 호출 함수
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 클릭한 툴바 메뉴 아이템의 id마다 다르게 실행하도록 설정
        when (item.itemId) {
            android.R.id.home -> {
                // 三메뉴 버튼 클릭 시 네비게이션 Drawer 열기
                drawerLayout.openDrawer(GravityCompat.START)
            }
            /* 
            // 검색 버튼 임시 삭제 (2022-02-11 박지훈 삭제)
            R.id.action_search -> {
                //검색 버튼 눌렀을 때
                Toast.makeText(applicationContext, "검색 이벤트 실행", Toast.LENGTH_LONG).show()
                return super.onOptionsItemSelected(item)
                // 검색 함수
                // EditText에 입력한 텍스트(주소, 지역, 가게이름)을 Geocoding 변환
                // * Geocoding : 주소 → 위도,경도
                // * ReverseCoding : 위도,경도 → 주소

            }
            */
            R.id.action_share -> {
                //공유 버튼 눌렀을 때
                Toast.makeText(applicationContext, "공유 이벤트 실행", Toast.LENGTH_LONG).show()
                return super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Drawer 내 아이템 클릭 시 처리하는 함수
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // 강직이형 User 메뉴 여기서 연결시키면 될 듯 합니다.
        when (item.itemId) {
            R.id.foodList -> Toast.makeText(this, "맛집리스트", Toast.LENGTH_SHORT).show()
            R.id.followers -> Toast.makeText(this, "팔로워", Toast.LENGTH_SHORT).show()
            R.id.userSettings -> Toast.makeText(this, "설정", Toast.LENGTH_SHORT).show()
            R.id.userLogout -> Toast.makeText(this, "로그아웃", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    // 뒤로가기 할 시 사이드, Nav바 닫는 기능 (Drawer Close)
    var backKeyPressedTime : Long = 0
    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers()
            //
            Toast.makeText(this, "뒤로가기", Toast.LENGTH_SHORT).show()
        } else if (placeLatitude != 0.0){
            super.onBackPressed()
        } else {
            // if user push back button at map
            if (currentTime > backKeyPressedTime + 2500) {
                backKeyPressedTime = System.currentTimeMillis()
                Toast.makeText(this@Map, "두 번 누르면 앱이 종료됩니다", Toast.LENGTH_SHORT).show()
                return
            } else {
                finishAffinity()
            }
        }
    }

    inner class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) :
        RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.right = verticalSpaceHeight
        }
    }

    private fun getFoodListData(Id: Int) {
        foodListViewModel = ViewModelProvider(this).get(FoodListViewModel::class.java)
        foodListViewModel.foodList.observe(this) {
            arrFoodList.clear()
            Toast.makeText(applicationContext, "getFoodData", Toast.LENGTH_LONG).show()
            arrFoodList = foodListViewModel.getFoodList(Id)
        }
    }

    private fun updateGroupAdapterList() {
        val groupList = foodGroupViewModel.getGroupList()
        if (groupList != null) {
            groupListAdapter.setSelectGroup(groupId)
            groupListAdapter.setListData(groupList)
            groupListAdapter.notifyDataSetChanged()
        } else groupListAdapter.setListDataClear()
    }

    private fun drawFoodMarker(fList: MutableList<Food>) {
        clearFoodMarker()

        Toast.makeText(applicationContext, "draw", Toast.LENGTH_LONG).show()
        for (i in 0 until fList.size) {
            val fName: String = fList[i].name
            Toast.makeText(applicationContext, fName, Toast.LENGTH_LONG).show()
            val fLatitude: Double = fList[i].latitude as Double
            val fLongitude: Double = fList[i].longitude as Double

            val fMarker = Marker()
            fMarker.position = LatLng(fLatitude, fLongitude)
            fMarker.map = naverMap
            fMarker.width = Marker.SIZE_AUTO
            fMarker.height = Marker.SIZE_AUTO
            fMarker.captionText = fName
            arrMarkerList.add(fMarker)
        }
    }

    private fun clearFoodMarker() {
        if (arrMarkerList.size <= 0) {
            return
        }

        Toast.makeText(applicationContext, "clearMarker", Toast.LENGTH_LONG).show()
        for (i in 0 until arrMarkerList.size) {
            arrMarkerList[i].map = null
        }
        arrMarkerList.clear()
    }

}