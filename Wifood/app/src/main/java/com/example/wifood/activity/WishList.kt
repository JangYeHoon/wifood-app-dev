package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wifood.R
import com.example.wifood.adapter.WishListAdapter
import com.example.wifood.databinding.ActivityWishListBinding
import com.example.wifood.entity.Group
import com.example.wifood.entity.Search
import com.example.wifood.entity.Wish
import com.example.wifood.viewmodel.WishListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WishList : AppCompatActivity() {
    lateinit var binding : ActivityWishListBinding
    private lateinit var wishListAdapter: WishListAdapter
    lateinit var wishListViewModel: WishListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWishListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // Drawer를 꺼낼 홈 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정

        val groupId = intent.getIntExtra("groupId", 0)

        wishListViewModel = ViewModelProvider(this, WishListViewModel.Factory(groupId)).get(WishListViewModel::class.java)
        wishListAdapter = WishListAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = wishListAdapter

        wishListViewModel.wishList.observe(this) {
            wishListAdapter.setListData(it)
            wishListAdapter.notifyDataSetChanged()
        }

        // wishlist add btn
        binding.groupAddButton.setOnClickListener {
            val intent = Intent(this@WishList, AddWishList::class.java)
            requestActivity.launch(intent)
        }
    }

    private val requestActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val searchResult = it.data?.getParcelableExtra<Search>("searchResult")
            val memo = it.data?.getStringExtra("memo")
            val wish = Wish(wishListViewModel.getWishListMaxId() + 1, searchResult!!.name, searchResult.fullAddress,
                memo!!, searchResult.latitude, searchResult.longitude)
            CoroutineScope(Dispatchers.IO).launch {
                wishListViewModel.wishListInsert(wish)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 클릭한 툴바 메뉴 아이템의 id마다 다르게 실행하도록 설정
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}