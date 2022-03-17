package com.example.wifood.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
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
import android.widget.Toast
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
import com.example.wifood.databinding.ActivityAddFoodListBinding
import com.example.wifood.entity.*
import com.example.wifood.viewmodel.ImageStoreViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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

    // 카메라, 갤러리 관련 변수
    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_GALLERY_TAKE = 2
    lateinit var currentPhotoPath: String
    lateinit var foodImageUri:Uri
    var imageList:ArrayList<String> = ArrayList(0)
    var imageCnt:Int = 0
    var imageUriList:ArrayList<Uri> = ArrayList(0)
    lateinit var imageStoreViewModel: ImageStoreViewModel

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

        // TODO "같은 것 끼리 묶어서 볼 수 있게 변경

        imageStoreViewModel = ViewModelProvider(this).get(ImageStoreViewModel::class.java)

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
                // TODO "visibility 설정 함수로 수정"
                binding.tableLayout2.visibility = View.VISIBLE
                binding.menuTable.visibility = View.VISIBLE
                binding.recyclerMenuGrade.visibility = View.VISIBLE
                binding.menuGradeText.visibility = View.VISIBLE
                binding.addCameraImageButton.visibility = View.VISIBLE
                binding.addGalleryImageButton.visibility = View.VISIBLE
                binding.foodImage.visibility = View.VISIBLE
                insertFood.myTasteGrade = binding.tasteGrade.rating.toDouble()
                insertFood.myCleanGrade = binding.cleanGrade.rating.toDouble()
                insertFood.myKindnessGrade = binding.kindnessGrade.rating.toDouble()
            } else {
                binding.tableLayout2.visibility = View.GONE
                binding.menuTable.visibility = View.GONE
                binding.recyclerMenuGrade.visibility = View.GONE
                binding.menuGradeText.visibility = View.GONE
                binding.addCameraImageButton.visibility = View.GONE
                binding.addGalleryImageButton.visibility = View.GONE
                binding.foodImage.visibility = View.GONE
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
            binding.memoText.setText(insertFood.memo)
            updateMenuListAdapter()
            if (insertFood.visited == 1) {
                listMenuGrade = insertFood.menuGrade
                updateMenuGradeListAdapter()
                binding.tasteGrade.rating = insertFood.myTasteGrade.toFloat()
                binding.kindnessGrade.rating = insertFood.myKindnessGrade.toFloat()
                binding.cleanGrade.rating = insertFood.myCleanGrade.toFloat()
                binding.isVisited.isChecked = true
                binding.tableLayout2.visibility = View.VISIBLE
                binding.menuTable.visibility = View.VISIBLE
                binding.recyclerMenuGrade.visibility = View.VISIBLE
                binding.menuGradeText.visibility = View.VISIBLE
                if (insertFood.imageUri.size > 0) {
                    downloadImage(insertFood.imageUri[0], insertFood.id)
                    imageList = insertFood.imageUri
                    imageCnt = imageList.size
                }
            }
        } else if (type == "add") {
            insertFood.groupId = intent.getIntExtra("groupId", -1)
            insertFood.id = intent.getIntExtra("foodId", -1)
        }
        binding.groupName.text = intent.getStringExtra("groupName").toString()

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

        // 카메라 버튼
        binding.addCameraImageButton.setOnClickListener { getCameraTakeImage() }
        // 갤러리 버튼
        binding.addGalleryImageButton.setOnClickListener { getGalleryImage() }

        // 맛집리스트를 추가할 수 있도록 설정된 food 정보를 FoodList Activity로 넘겨줌
        binding.saveBtn.setOnClickListener {
            insertFood.memo = binding.memoText.text.toString()
            if (insertFood.visited == 0)
                listMenuGrade.clear()
//            imageStoreViewModel.insertFoodImage(imageUriList, insertFood.id)
            insertFood.imageUri = imageList
            insertFood.menu = menuList
            insertFood.menuGrade = listMenuGrade
            if (insertFood.name != "None" && insertFood.groupId != -1) {
                val intent = Intent().apply {
                    putExtra("food", insertFood)
                    if (type == "edit") {
                        putExtra("type", 1)
                        // TODO "food data class 변경해서 food에 group name 추가해서 food만 넘겨주게 변경"
                        putExtra("groupName", binding.groupName.text)
                    }
                    else {
                        putExtra("type", 0)
                        putExtra("imageUri", imageUriList)
                    }
                }
                setResult(RESULT_OK, intent)
                // TODO "ViewModel로 수정"
                if (imageList.size > 0) {
                    val storage = FirebaseStorage.getInstance().reference
                    val uploadTask = storage.child(insertFood.id.toString() + "/").child("1.png")
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

    // TODO "매개변수로 menuList 값 받아와서 처리
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

    private fun getCameraTakeImage() {
        var cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        var storagePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        // 카메라 권한 확인
        if (cameraPermission == PackageManager.PERMISSION_DENIED
            || storagePermission == PackageManager.PERMISSION_DENIED) {
            // 카메라 권한 요청
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                REQUEST_IMAGE_CAPTURE)
        } else {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                if (takePictureIntent.resolveActivity(this.packageManager) != null) {
                    // 촬영한 사진을 파일로 생성
                    val photoFile: File = createImageFile()
                    if (Build.VERSION.SDK_INT < 24) {
                        val photoURI = Uri.fromFile(photoFile)
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                    else{
                        photoFile.also {
                            val photoURI: Uri = FileProvider.getUriForFile(
                                this, "com.example.wifood.fileprovider", it)
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                        }
                    }
                }
            }
        }
    }

    private fun getGalleryImage() {
        var storagePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        // 갤러리 권한 확인
        if (storagePermission == PackageManager.PERMISSION_DENIED) {
            // 갤러리 권한 요청
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_IMAGE_CAPTURE)
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
                if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
                    // TODO 이미지 한개만 저장일 때 필요 만약 여러개 저장으로 바뀌면 삭제
                    imageUriList.clear()
                    imageList.clear()
                    imageCnt = 0
                    // 카메라로부터 받은 데이터가 있을경우에만
                    // TODO 동일한 부분 함수로 빼기
                    val file = File(currentPhotoPath)
                    binding.foodImage.setImageURI(Uri.fromFile(file))
                    foodImageUri = Uri.fromFile(file)
                    imageUriList.add(Uri.fromFile(file))
                    imageCnt += 1
                    imageList.add(imageCnt.toString())
                    binding.foodImage.visibility = View.VISIBLE
                }
            }
            2 -> {
                if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY_TAKE) {
                    // TODO 이미지 한개만 저장일 때 필요 만약 여러개 저장으로 바뀌면 삭제
                    imageUriList.clear()
                    imageList.clear()
                    imageCnt = 0
                    binding.foodImage.setImageURI(data?.data)
                    foodImageUri = data?.data!!
                    imageUriList.add(data.data!!)
                    imageCnt += 1
                    imageList.add(imageCnt.toString())
                    binding.foodImage.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun downloadImage(idx: String, foodId: Int) {
        val storage:FirebaseStorage = FirebaseStorage.getInstance()
        val storageRef: StorageReference = storage.reference.child("$foodId/$idx.png")
        storageRef.downloadUrl.addOnCompleteListener {
            if (it.isSuccessful) {
                Glide.with(this@AddFoodList).load(it.result).into(binding.foodImage)
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