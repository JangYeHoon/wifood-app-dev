package com.example.wifood.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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
import com.example.wifood.databinding.ActivityEditPlaceBinding
import com.example.wifood.domain.entity.Group
import com.example.wifood.domain.entity.Menu
import com.example.wifood.domain.entity.MenuGrade
import com.example.wifood.domain.entity.Search
import com.example.wifood.entity.*
import com.example.wifood.presentation.viewmodel.ImageStoreViewModel
import com.example.wifood.presentation.viewmodel.PlaceViewModel
import com.example.wifood.presentation.viewmodel.SearchPlaceViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

const val REQUEST_IMAGE_CAPTURE = 1
const val REQUEST_GALLERY_TAKE = 2
class EditPlaceView : AppCompatActivity() {
    var imageUriList: ArrayList<Uri> = ArrayList(0)

    lateinit var binding: ActivityEditPlaceBinding
    lateinit var searchResult: Search
    lateinit var adapterMenuName: MenuNameAdapter
    lateinit var adapterMenuGrade: MenuGradeAdapter
    lateinit var inputMethodManager: InputMethodManager
    lateinit var currentPhotoPath: String
    lateinit var imageStoreViewModel: ImageStoreViewModel
    lateinit var placeViewModel: PlaceViewModel
    lateinit var searchPlaceViewModel: SearchPlaceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        val type = intent.getStringExtra("type")

        // ?????? ??????
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // ???????????? ?????? ?????????
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // ????????? ????????? ???????????? ??????
        supportActionBar?.title = "??????????????? ??????"

        // ?????? ?????? ?????? ????????? ????????? ??????
        adapterMenuName = MenuNameAdapter(this)
        binding.recyclerViewMenuList.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerViewMenuList.adapter = adapterMenuName
//        updateMenuListAdapter(insertPlace.menu)

        // ?????? ??????, ?????? ?????? ????????? ????????? ??????
        adapterMenuGrade = MenuGradeAdapter(this)
        binding.recyclerViewMenuGradeList.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMenuGradeList.adapter = adapterMenuGrade
//        updateMenuGradeListAdapter(insertPlace.menuGrade)

        imageStoreViewModel = ViewModelProvider(this).get(ImageStoreViewModel::class.java)
        placeViewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)
        placeViewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)
        searchPlaceViewModel = ViewModelProvider(
            this,
            SearchPlaceViewModel.Factory(this)
        ).get(SearchPlaceViewModel::class.java)

        // ??????/????????? ?????? ?????? ?????? ??? ??????
        initActivityView(type!!)

        // ???, ??????, ????????? ?????? ?????? ?????? ?????? ????????? ??????
        binding.ratingBarTasteGrade.setOnRatingBarChangeListener { ratingBar, rating, _ ->
            if (rating <= 0.5f) ratingBar.rating = 0.5f
            placeViewModel.setTasteGrade(rating.toDouble())
        }
        binding.ratingBarCleanGrade.setOnRatingBarChangeListener { ratingBar, rating, _ ->
            if (rating < 0.5f) ratingBar.rating = 0.5f
            placeViewModel.setCleanGrade(rating.toDouble())
        }
        binding.ratingBarKindnessGrade.setOnRatingBarChangeListener { ratingBar, rating, _ ->
            if (rating < 0.5f) ratingBar.rating = 0.5f
            placeViewModel.setKindnessGrade(rating.toDouble())
        }

        // ?????? ?????? ??????
        binding.switchIsVisit.setOnCheckedChangeListener { _, onSwitch ->
            placeViewModel.setVisited(onSwitch.toInt())
            updateActivityViewByVisit(onSwitch)
            if (onSwitch) {
                placeViewModel.updatePlaceGrade(
                    binding.ratingBarTasteGrade.rating.toDouble(),
                    binding.ratingBarCleanGrade.rating.toDouble(),
                    binding.ratingBarKindnessGrade.rating.toDouble()
                )
            } else {
                placeViewModel.updatePlaceGrade(0.0, 0.0, 0.0)
            }
        }

        // ?????? ?????? ??????
        binding.imageButtonNextInsertMenu.setOnClickListener {
            insertMenu()
        }
        // ?????? ???????????? ?????? ?????? ??? ??????
        binding.editTextMenuName.setOnEditorActionListener { _, i, _ ->
            var handled = false
            if (i == EditorInfo.IME_ACTION_DONE) {
                insertMenu()
                handled = true
            }
            handled
        }

        // ?????? ?????? ??????
        adapterMenuName.setMenuClickListener(object : MenuNameAdapter.MenuClickListener {
            override fun onClick(view: View, position: Int, menu: Menu) {
                placeViewModel.deleteMenuByIdx(position)
                updateMenuListAdapter(placeViewModel.getPlaceMenuList())
            }
        })

        // ?????? ?????? ??????
        binding.imageButtonAddMenuGrade.setOnClickListener {
            val name: String = binding.editTextMenuGradeName.text.toString()
            val price: Int = binding.editTextMenuPrice.text.toString().toInt()
            val grade: Double = binding.ratingBarMenuGrade.rating.toDouble()
            val memo: String = binding.editTextMenuMemo.text.toString()
            if (name != "") {
                // ?????? ?????? ??? ???????????? ??????
                placeViewModel.insertMenuGrade(MenuGrade(name, price, grade, memo))
                updateMenuGradeListAdapter(placeViewModel.getMenuGradeList())

                // ?????? ?????? ?????? view ?????????
                inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                binding.editTextMenuGradeName.setText("")
                binding.editTextMenuPrice.setText("")
                binding.editTextMenuMemo.setText("")
                binding.ratingBarMenuGrade.rating = 0.5f
            }
        }

        // ?????? ?????? SearchPlace Activity??? ??????
        if (type != "edit") {
            binding.textViewPlaceName.setOnClickListener {
                val intent = Intent(this@EditPlaceView, SearchPlaceView::class.java).apply {}
                requestActivity.launch(intent)
            }
            binding.imageButtonNextSearchPlace.setOnClickListener {
                val intent = Intent(this@EditPlaceView, SearchPlaceView::class.java).apply {}
                requestActivity.launch(intent)
            }
        }

        // ?????? ?????? ?????????????????? ??????
        binding.textViewGroupName.setOnClickListener {
            val bottomSheet = GroupSelectBottom()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
        binding.imageButtonNextGroup.setOnClickListener {
            val bottomSheet = GroupSelectBottom()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        // ????????? ??????
        binding.buttonCamera.setOnClickListener { getCameraTakeImage() }
        // ????????? ??????
        binding.buttonGallery.setOnClickListener { getGalleryImage() }

        // ?????????????????? ????????? ??? ????????? ????????? place ????????? PlaceList Activity??? ?????????
        binding.buttonSave.setOnClickListener {
            placeViewModel.setPlaceMemo(binding.editTextPlaceMemo.text.toString())
            if (!placeViewModel.isVisited()) {
                placeViewModel.initImageList()
                placeViewModel.initMenuGrade()
            }
            if (!placeViewModel.isPlaceNameEmpty() && !placeViewModel.isPlaceGroupEmpty()) {
                val intent = Intent().apply {
                    putExtra("place", placeViewModel.getPlaceInstance())
                    if (type == "edit") {
                        putExtra("type", 1)
                        putExtra("groupName", binding.textViewGroupName.text)
                    } else {
                        putExtra("type", 0)
                        putExtra("imageUri", imageUriList)
                    }
                }
                setResult(RESULT_OK, intent)
                if (!placeViewModel.isImageEmpty() && imageUriList.isNotEmpty()) {
                    imageStoreViewModel.insertPlaceImage(imageUriList, placeViewModel.getPlaceId())
                        .addOnSuccessListener {
                            finish()
                        }
                } else {
                    finish()
                }
            }
        }
    }

    private fun Boolean.toInt() = if (this) 1 else 0

    // SearchPlace Activity????????? ?????? ???????????? ????????? name, bizname, address??? ?????? text ??????
    private val requestActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                searchPlaceViewModel.setSearchResultInstance(it.data?.getParcelableExtra("searchResult")!!)
                binding.textViewPlaceName.text = searchPlaceViewModel.getSearchName()
                binding.textViewAddress.text = searchPlaceViewModel.getSearchAddress()
                placeViewModel.setPlaceName(searchPlaceViewModel.getSearchName())
                placeViewModel.setPlaceAddress(searchPlaceViewModel.getSearchAddress())
                placeViewModel.setPlaceLatitude(searchPlaceViewModel.getSearchLatitude())
                placeViewModel.setPlaceLongitude(searchPlaceViewModel.getSearchLongitude())
            }
        }

    private fun initActivityView(type: String) {
        if (type == "edit") {
            placeViewModel.initPlace(intent.getParcelableExtra("place")!!)
            binding.textViewPlaceName.text = placeViewModel.getPlaceName()
            binding.textViewAddress.text = placeViewModel.getPlaceAddress()
            binding.editTextPlaceMemo.setText(placeViewModel.getPlaceMemo())
            updateMenuListAdapter(placeViewModel.getPlaceMenuList())
            updateActivityViewByVisit(placeViewModel.isVisited())
            if (placeViewModel.isVisited()) {
                updateMenuGradeListAdapter(placeViewModel.getMenuGradeList())
                binding.ratingBarTasteGrade.rating = placeViewModel.getTasteGrade().toFloat()
                binding.ratingBarKindnessGrade.rating = placeViewModel.getKindnessGrade().toFloat()
                binding.ratingBarCleanGrade.rating = placeViewModel.getCleanGrade().toFloat()
                binding.switchIsVisit.isChecked = true
                if (!placeViewModel.isImageEmpty()) {
                    imageStoreViewModel.getPlaceImage(
                        placeViewModel.getImageName().toInt(),
                        placeViewModel.getPlaceId()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Glide.with(this@EditPlaceView).load(it.result)
                                .into(binding.imageViewPlace)
                            imageUriList.add(it.result)
                        }
                    }
                }
            }
        } else if (type == "add") {
            placeViewModel.setPlaceGroupId(intent.getIntExtra("groupId", -1))
            placeViewModel.setPlaceId(intent.getIntExtra("placeId", -1))
        }
        binding.textViewGroupName.text = intent.getStringExtra("groupName").toString()
    }

    // ?????? ????????? ???????????? ?????? ??? ?????????
    private fun insertMenu() {
        if (binding.editTextMenuName.text.toString() != "") {
            placeViewModel.insertPlaceMenu(Menu(binding.editTextMenuName.text.toString()))
            updateMenuListAdapter(placeViewModel.getPlaceMenuList())
            inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.editTextMenuName.windowToken, 0)
            binding.editTextMenuName.setText("")
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

    // ?????? ????????? ?????? view ??????
    private fun updateActivityViewByVisit(isVisit: Boolean) {
        val view: Int =
            if (isVisit) View.VISIBLE
            else View.GONE

        binding.tableLayoutMenuGrade.visibility = view
        binding.tableLayoutMenuGradeTitle.visibility = view
        binding.recyclerViewMenuGradeList.visibility = view
        binding.tableLayoutPlaceGrade.visibility = view
        binding.buttonCamera.visibility = view
        binding.buttonGallery.visibility = view
        binding.imageViewPlace.visibility = view
    }

    // ?????? ?????? fragment?????? ?????? ??? ???????????? ??????
    fun setGroupByGroupEntity(group: Group) {
        binding.textViewGroupName.text = group.name
        placeViewModel.setPlaceGroupId(group.id)
    }

    private fun getCameraTakeImage() {
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val storagePermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        // ????????? ?????? ??????
        if (cameraPermission == PackageManager.PERMISSION_DENIED
            || storagePermission == PackageManager.PERMISSION_DENIED
        ) {
            // ????????? ?????? ??????
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                REQUEST_IMAGE_CAPTURE
            )
        } else {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                if (takePictureIntent.resolveActivity(this.packageManager) != null) {
                    // ????????? ????????? ????????? ??????
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
        // ????????? ?????? ??????
        if (storagePermission == PackageManager.PERMISSION_DENIED) {
            // ????????? ?????? ??????
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
        placeViewModel.initImageList()
        binding.imageViewPlace.setImageURI(imageUri)
        imageUriList.add(imageUri)
        placeViewModel.insertImageName("1")
        binding.imageViewPlace.visibility = View.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // ?????? ????????? ???????????? ??????
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}