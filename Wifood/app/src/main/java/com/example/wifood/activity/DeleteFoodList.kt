package com.example.wifood.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wifood.R
import com.example.wifood.adapter.DeletePlaceAdapter
import com.example.wifood.databinding.ActivityDeleteFoodListBinding
import com.example.wifood.entity.Place

class DeleteFoodList : AppCompatActivity() {
    lateinit var binding : ActivityDeleteFoodListBinding
    lateinit var deletePlaceAdapter: DeletePlaceAdapter
    var selectedFoodList : SparseBooleanArray = SparseBooleanArray(0)   // 선택된 항목인지 체크할 리스트
    var deleteFoodList : ArrayList<Int> = ArrayList(0)      // 삭제할 맛집에 대한 id를 저장하는 리스트
    // TODO "해당 Activity 삭제"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 설정
        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // 뒤로가기 홈 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정
        supportActionBar?.title = "맛집리스트 삭제"

        // 삭제할 맛집을 선택할 수 있도록 foodlist를 받아와서 해당 foodlist를 이용해 recyclerview에 뿌려줌
        var foodlist = intent.getParcelableArrayListExtra<Place>("foodlist")
        deletePlaceAdapter = DeletePlaceAdapter(this)
        binding.deleteRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.deleteRecyclerView.adapter = deletePlaceAdapter
        binding.deleteRecyclerView.addItemDecoration(DividerItemDecoration(this, 1))
        deletePlaceAdapter.setListData(foodlist!!)

        // 삭제버튼을 누르면 선택된 맛집에 대한 id list를 FoodListActivity로 넘겨줌
        binding.deleteBtn.setOnClickListener {
            if (deleteFoodList.size != 0) {
                val intent = Intent().apply {
                    putExtra("deleteIdList", deleteFoodList)
                    putExtra("type", 2)
                }
                setResult(RESULT_OK, intent)
            } else setResult(RESULT_CANCELED)
            finish()
        }

        // foodlist에서 클릭이 되었는지 체크하는 함수
        deletePlaceAdapter.setDeleteFoodListClickListener(object: DeletePlaceAdapter.DeleteFoodListClickListener {
            override fun onClick(view: View, position: Int, item: Place) {
                // 선택된 맛집이 기존에 선택되어 있던 맛집이면 삭제리스트에서 제외
                if (selectedFoodList.get(position, false)) {
                    selectedFoodList.delete(position)
                    view.setBackgroundColor(Color.WHITE)
                    deleteFoodList.remove(item.id)
                } else {    // 선택된 맛집이 기존에 선택되어 있지 않으면 추가
                    selectedFoodList.put(position, true)
                    view.setBackgroundColor(Color.DKGRAY)
                    deleteFoodList.add(item.id)
                }
            }
        })
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