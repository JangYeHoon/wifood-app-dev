package com.example.wifood.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wifood.adapter.WishListAdapter
import com.example.wifood.databinding.ActivityWishListBinding
import com.example.wifood.viewmodel.WishListViewModel

class WishList : AppCompatActivity() {
    lateinit var binding : ActivityWishListBinding
    private lateinit var wishListAdapter: WishListAdapter
    lateinit var wishListViewModel: WishListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWishListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        wishListViewModel = ViewModelProvider(this).get(WishListViewModel::class.java)
        wishListAdapter = WishListAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = wishListAdapter

        wishListViewModel.wishList.observe(this) {
            wishListAdapter.setListData(it)
            wishListAdapter.notifyDataSetChanged()
        }
    }
}