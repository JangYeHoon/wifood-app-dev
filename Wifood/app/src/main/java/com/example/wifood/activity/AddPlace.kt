package com.example.wifood.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.wifood.R
import com.example.wifood.adapter.*
import com.example.wifood.databinding.ActivityAddPlaceBinding
import com.example.wifood.entity.*
import com.example.wifood.viewmodel.ImageStoreViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

const val REQUEST_IMAGE_CAPTURE = 1
const val REQUEST_GALLERY_TAKE = 2

class AddPlace : AppCompatActivity() {
    var imageCnt: Int = 0
    var insertPlace: Place = Place()
    var imageList: ArrayList<String> = ArrayList(0)
    var imageUriList: ArrayList<Uri> = ArrayList(0)

    lateinit var binding: ActivityAddPlaceBinding
    lateinit var searchResult: Search
    lateinit var adapterMenuName: MenuNameAdapter
    lateinit var adapterMenuGrade: MenuGradeAdapter
    lateinit var inputMethodManager: InputMethodManager
    lateinit var currentPhotoPath: String
    lateinit var foodImageUri: Uri
    lateinit var imageStoreViewModel: ImageStoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        val type = intent.getStringExtra("type")

        // 툴바 설정
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // 뒤로가기 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정
        supportActionBar?.title = "맛집리스트 추가"

        // 수정/추가에 따른 초기 화면 값 설정
        initActivityView(type!!)

        // 메뉴 이름 관련 리스트 어뎁터 설정
        adapterMenuName = MenuNameAdapter(this)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerView.adapter = adapterMenuName
        updateMenuListAdapter(insertPlace.menu)

        // 메뉴 평가, 가격 관련 리스트 어뎁터 설정
        adapterMenuGrade = MenuGradeAdapter(this)
        binding.recyclerMenuGrade.layoutManager = LinearLayoutManager(this)
        binding.recyclerMenuGrade.adapter = adapterMenuGrade
        updateMenuGradeListAdapter(insertPlace.menuGrade)

        imageStoreViewModel = ViewModelProvider(this).get(ImageStoreViewModel::class.java)

        // 맛, 청결, 친절에 대한 별점 변경 관련 리스너 설정
        binding.tasteGrade.setOnRatingBarChangeListener { ratingBar, rating, _ ->
            if (rating <= 0.5f) ratingBar.rating = 0.5f
            insertPlace.myTasteGrade = rating.toDouble()
        }
        binding.cleanGrade.setOnRatingBarChangeListener { ratingBar, rating, _ ->
            if (rating < 0.5f) ratingBar.rating = 0.5f
            insertPlace.myCleanGrade = rating.toDouble()
        }
        binding.kindnessGrade.setOnRatingBarChangeListener { ratingBar, rating, _ ->
            if (rating < 0.5f) ratingBar.rating = 0.5f
            insertPlace.myKindnessGrade = rating.toDouble()
        }

        // 방문 여부 체크
        binding.isVisited.setOnCheckedChangeListener { _, onSwitch ->
            insertPlace.visited = onSwitch.toInt()
            updateActivityViewByVisit(onSwitch)
            if (onSwitch) {
                updatePlaceGrade(
                    binding.tasteGrade.rating.toDouble(),
                    binding.cleanGrade.rating.toDouble(),
                    binding.kindnessGrade.rating.toDouble()
                )
            } else {
                updatePlaceGrade(0.0, 0.0, 0.0)
            }
        }

        // 메뉴 추가 버튼
        binding.insertMenu.setOnClickListener {
            insertMenu()
        }
        // 메뉴 입력에서 엔터 입력 시 추가
        binding.editMenu.setOnEditorActionListener { _, i, _ ->
            var handled = false
            if (i == EditorInfo.IME_ACTION_DONE) {
                insertMenu()
                handled = true
            }
            handled
        }

        // 메뉴 삭제 버튼
        adapterMenuName.setMenuClickListener(object : MenuNameAdapter.MenuClickListener {
            override fun onClick(view: View, position: Int, menu: Menu) {
                insertPlace.menu.removeAt(position)
                updateMenuListAdapter(insertPlace.menu)
            }
        })

        // 메뉴 평가 추가
        binding.addMenuGradeButton.setOnClickListener {
            val name: String = binding.editMenuName.text.toString()
            val price: Int = binding.editMenuPrice.text.toString().toInt()
            val grade: Double = binding.menuGrade.rating.toDouble()
            val memo: String = binding.editMenuMemo.text.toString()
            if (name != "") {
                // 메뉴 평가 값 리스트에 추가
                insertPlace.menuGrade.add(MenuGrade(name, price, grade, memo))
                updateMenuGradeListAdapter(insertPlace.menuGrade)

                // 메뉴 평가 관련 view 초기화
                inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                binding.editMenuName.setText("")
                binding.editMenuMemo.setText("")
                binding.editMenuPrice.setText("")
                binding.menuGrade.rating = 0.5f
            }
        }

        // 맛집 검색 SearchPlace Activity로 이동
        if (type != "edit") {
            binding.searchName.setOnClickListener {
                val intent = Intent(this@AddPlace, SearchPlace::class.java).apply {}
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

        // 카메라 버튼
        binding.addCameraImageButton.setOnClickListener { getCameraTakeImage() }
        // 갤러리 버튼
        binding.addGalleryImageButton.setOnClickListener { getGalleryImage() }

        // 맛집리스트를 추가할 수 있도록 설정된 food 정보를 FoodList Activity로 넘겨줌
        binding.saveBtn.setOnClickListener {
            insertPlace.memo = binding.memoText.text.toString()
            if (insertPlace.visited == 0) {
                imageList.clear()
                insertPlace.menuGrade.clear()
            }
//            imageStoreViewModel.insertFoodImage(imageUriList, insertFood.id)
            insertPlace.imageUri = imageList
            if (insertPlace.name != "None" && insertPlace.groupId != -1) {
                val intent = Intent().apply {
                    putExtra("food", insertPlace)
                    if (type == "edit") {
                        putExtra("type", 1)
                        putExtra("groupName", binding.groupName.text)
                    } else {
                        putExtra("type", 0)
                        putExtra("imageUri", imageUriList)
                    }
                }
                setResult(RESULT_OK, intent)
                // TODO "ViewModel로 수정"
                if (imageList.size > 0) {
                    val storage = FirebaseStorage.getInstance().reference
                    val uploadTask = storage.child(insertPlace.id.toString() + "/").child("1.png")
                        .putFile(imageUriList[0])
                    uploadTask.addOnSuccessListener {
                        finish()
                    }
                } else {
                    finish()
                }
            }
        }
    }

    private fun Boolean.toInt() = if (this) 1 else 0

    // SearchPlace Activity로부터 받은 정보들을 이용해 name, bizname, address에 대한 text 설정
    private val requestActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                searchResult = it.data?.getParcelableExtra("searchResult")!!
                binding.searchName.text = searchResult.name
                binding.searchAddress.text = searchResult.fullAddress
                insertPlace.name = searchResult.name
                insertPlace.address = searchResult.fullAddress
                insertPlace.latitude = searchResult.latitude
                insertPlace.longitude = searchResult.longitude
            }
        }

    private fun initActivityView(type: String) {
        if (type == "edit") {
            insertPlace = intent.getParcelableExtra("food")!!
            binding.searchName.text = insertPlace.name
            binding.searchAddress.text = insertPlace.address
            binding.memoText.setText(insertPlace.memo)
            updateMenuListAdapter(insertPlace.menu)
            updateActivityViewByVisit(insertPlace.visited == 1)
            if (insertPlace.visited == 1) {
                updateMenuGradeListAdapter(insertPlace.menuGrade)
                binding.tasteGrade.rating = insertPlace.myTasteGrade.toFloat()
                binding.kindnessGrade.rating = insertPlace.myKindnessGrade.toFloat()
                binding.cleanGrade.rating = insertPlace.myCleanGrade.toFloat()
                binding.isVisited.isChecked = true
                if (insertPlace.imageUri.size > 0) {
                    getImageToFirebase(insertPlace.imageUri[0], insertPlace.id)
                    imageList = insertPlace.imageUri
                    imageCnt = imageList.size
                }
            }
        } else if (type == "add") {
            insertPlace.groupId = intent.getIntExtra("groupId", -1)
            insertPlace.id = intent.getIntExtra("foodId", -1)
        }
        binding.groupName.text = intent.getStringExtra("groupName").toString()
    }

    // 메뉴 리스트 추가하고 관련 값 초기화
    private fun insertMenu() {
        if (binding.editMenu.text.toString() != "") {
            insertPlace.menu.add(Menu(binding.editMenu.text.toString()))
            updateMenuListAdapter(insertPlace.menu)
            inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.editMenu.windowToken, 0)
            binding.editMenu.setText("")
        }
    }

    private fun updateMenuListAdapter(menuList: ArrayList<Menu>) {
        adapterMenuName.setListData(menuList)
        adapterMenuName.notifyDataSetChanged()
    }

    private fun updateMenuGradeListAdapter(menuGradeList: ArrayList<MenuGrade>) {
        adapterMenuGrade.setListData(menuGradeList)
        adapterMenuGrade.notifyDataSetChanged()
    }

    // 방문 여부에 따라 view 설정
    private fun updateActivityViewByVisit(isVisit: Boolean) {
        val view: Int =
            if (isVisit) View.VISIBLE
            else View.GONE

        binding.tableLayout2.visibility = view
        binding.menuTable.visibility = view
        binding.recyclerMenuGrade.visibility = view
        binding.menuGradeText.visibility = view
        binding.addCameraImageButton.visibility = view
        binding.addGalleryImageButton.visibility = view
        binding.foodImage.visibility = view
    }

    private fun updatePlaceGrade(taste: Double, clean: Double, kindness: Double) {
        insertPlace.myTasteGrade = taste
        insertPlace.myCleanGrade = clean
        insertPlace.myKindnessGrade = kindness
    }

    // 그룹 선택 fragment에서 선택 값 가져오는 함수
    fun setGroupByGroupEntity(group: Group) {
        binding.groupName.text = group.name
        insertPlace.groupId = group.id
    }

    private fun getCameraTakeImage() {
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val storagePermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        // 카메라 권한 확인
        if (cameraPermission == PackageManager.PERMISSION_DENIED
            || storagePermission == PackageManager.PERMISSION_DENIED
        ) {
            // 카메라 권한 요청
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                REQUEST_IMAGE_CAPTURE
            )
        } else {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                if (takePictureIntent.resolveActivity(this.packageManager) != null) {
                    // 촬영한 사진을 파일로 생성
                    val photoFile: File = createImageFile()
                    photoFile.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            this, "com.example.wifood.fileprovider", it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                }
            }
        }
    }

    private fun getGalleryImage() {
        val storagePermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        // 갤러리 권한 확인
        if (storagePermission == PackageManager.PERMISSION_DENIED) {
            // 갤러리 권한 요청
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_IMAGE_CAPTURE
            )
        } else {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_GALLERY_TAKE)
        }
    }

    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
                    val file = File(currentPhotoPath)
                    updateImageFile(Uri.fromFile(file))
                }
            }
            2 -> {
                if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY_TAKE) {
                    data?.data?.let { updateImageFile(it) }
                }
            }
        }
    }

    private fun updateImageFile(imageUri: Uri) {
        imageUriList.clear()
        imageList.clear()
        imageCnt = 0
        binding.foodImage.setImageURI(imageUri)
        foodImageUri = imageUri
        imageUriList.add(imageUri)
        imageCnt += 1
        imageList.add(imageCnt.toString())
        binding.foodImage.visibility = View.VISIBLE
    }

    private fun getImageToFirebase(idx: String, foodId: Int) {
        val storage: FirebaseStorage = FirebaseStorage.getInstance()
        val storageRef: StorageReference = storage.reference.child("$foodId/$idx.png")
        storageRef.downloadUrl.addOnCompleteListener {
            if (it.isSuccessful) {
                Glide.with(this@AddPlace).load(it.result).into(binding.foodImage)
                imageUriList.add(it.result)
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