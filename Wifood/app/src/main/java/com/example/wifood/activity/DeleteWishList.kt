package com.example.wifood.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wifood.R
import com.example.wifood.adapter.DeleteWishListAdapter
import com.example.wifood.databinding.ActivityDeleteWishListBinding
import com.example.wifood.entity.Wish

class DeleteWishList : AppCompatActivity() {
    lateinit var binding : ActivityDeleteWishListBinding
    lateinit var deleteWishListAdapter : DeleteWishListAdapter
    var selectedWishList : SparseBooleanArray = SparseBooleanArray(0)
    var deleteWishList : ArrayList<Int> = ArrayList(0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteWishListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // Drawer를 꺼낼 홈 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정

        supportActionBar?.title = "위시리스트 삭제"

        val wishlist = intent.getParcelableArrayListExtra<Wish>("wishlist")
        deleteWishListAdapter = DeleteWishListAdapter(this)
        binding.deleteRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.deleteRecyclerView.adapter = deleteWishListAdapter
        binding.deleteRecyclerView.addItemDecoration(DividerItemDecoration(this, 1))
        deleteWishListAdapter.setListData(wishlist!!)

        binding.deleteBtn.setOnClickListener {
            if (deleteWishList.size != 0) {
                val intent = Intent().apply {
                    putExtra("deleteIdList", deleteWishList)
                    putExtra("type", 2)
                }
                setResult(RESULT_OK, intent)
            } else setResult(RESULT_CANCELED)
            finish()
        }

        deleteWishListAdapter.setDeleteWishListClickListener(object: DeleteWishListAdapter.DeleteWishListClickListener {
            override fun onClick(view: View, position: Int, item: Wish) {
                if (selectedWishList.get(position, false)) {
                    selectedWishList.delete(position)
                    view.setBackgroundColor(Color.WHITE)
                    deleteWishList.remove(item.id)
                } else {
                    selectedWishList.put(position, true)
                    view.setBackgroundColor(Color.DKGRAY)
                    deleteWishList.add(item.id)
                }
            }
        })
    }
}