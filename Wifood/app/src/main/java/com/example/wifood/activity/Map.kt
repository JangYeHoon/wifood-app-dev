package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import androidx.annotation.UiThread
import com.example.wifood.R

import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import androidx.core.view.GravityCompat

import androidx.drawerlayout.widget.DrawerLayout
import android.widget.Toast
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.google.android.material.navigation.NavigationView
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons

class Map : AppCompatActivity(), OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    private lateinit var navigationView: NavigationView
    private lateinit var drawerLayout: DrawerLayout

    // Marker 예제
    private val foodMarker = Marker()
    private val wishMarker = Marker()

    // onCreate()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

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

        // 예훈이형 메뉴로 Go (개발 임시 버튼 Event)
        val btn = findViewById<Button>(R.id.goGroupSelect) as Button
        btn.setOnClickListener {
            val intent = Intent(this, GroupSelect::class.java)
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
                naverMap.locationTrackingMode = LocationTrackingMode.None
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

        // 현재위치 표시
        val uiSettings = naverMap.uiSettings
        uiSettings.isLocationButtonEnabled = true
        naverMap.locationTrackingMode =
            LocationTrackingMode.Face   // 위치추적 활성화, 현위치 오버레이, 카메라 좌표, 베어링이 사용자의 위치 및 방향에 따라 움직임

        // Marker 예제
        // 지도상에 마커 표시 → 맛집 표시로 활용하면 될 듯
        foodMarker.position = LatLng(37.55285848882371, 127.14371145563707)
        foodMarker.map = naverMap
        foodMarker.icon = MarkerIcons.RED
        foodMarker.captionText = "하남돼지집 명일역"

        wishMarker.position = LatLng(37.55125881330635, 127.14665918080502)
        wishMarker.map = naverMap
        wishMarker.icon = MarkerIcons.BLUE
        wishMarker.captionText = "회밀리"
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
            R.id.action_search -> {
                //검색 버튼 눌렀을 때
                Toast.makeText(applicationContext, "검색 이벤트 실행", Toast.LENGTH_LONG).show()
                return super.onOptionsItemSelected(item)
                // 검색 함수
                // EditText에 입력한 텍스트(주소, 지역, 가게이름)을 Geocoding 변환
                // * Geocoding : 주소 → 위도,경도
                // * ReverseCoding : 위도,경도 → 주소

            }
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
            R.id.menu_item1 -> Toast.makeText(this, "메뉴1 실행", Toast.LENGTH_SHORT).show()
            R.id.menu_item2 -> Toast.makeText(this, "메뉴2 실행", Toast.LENGTH_SHORT).show()
            R.id.menu_item3 -> Toast.makeText(this, "메뉴3 실행", Toast.LENGTH_SHORT).show()
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
        } else {
            // if user push back button at map
            if (currentTime > backKeyPressedTime + 2500) {
                backKeyPressedTime = System.currentTimeMillis()
                Toast.makeText(this@Map, "두 번 누르면 앱이 종료됩니다", Toast.LENGTH_SHORT).show()
                return
            }else{
                finishAffinity()
            }
        }
    }


}