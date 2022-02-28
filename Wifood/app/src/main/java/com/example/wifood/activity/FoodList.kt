package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.example.wifood.R
import com.example.wifood.adapter.FoodListAdapter
import com.example.wifood.adapter.GroupNameAdapter
import com.example.wifood.databinding.ActivityFoodListBinding
import com.example.wifood.entity.Food
import com.example.wifood.entity.Group
import com.example.wifood.entity.Search
import com.example.wifood.viewmodel.FoodGroupViewModel
import com.example.wifood.viewmodel.FoodListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodList : AppCompatActivity() {
    lateinit var binding : ActivityFoodListBinding
    private lateinit var foodListAdapter: FoodListAdapter
    lateinit var foodListViewModel: FoodListViewModel
    private lateinit var groupListAdapter: GroupNameAdapter
    lateinit var groupListViewModel: FoodGroupViewModel
    var groupId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 설정
        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // 뒤로가기 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정
        supportActionBar?.title = intent.getStringExtra("groupName")

        // 데이터베이스 접근을 위한 food group id정보 받아옴
        groupId = intent.getIntExtra("groupId", 0)

        // 데이터베이스에서 받아온 foodlist 정보를 이용해 recyclerView 설정
        foodListAdapter = FoodListAdapter(this)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = foodListAdapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, 1))
        setFoodListView()

        groupListViewModel = ViewModelProvider(this).get(FoodGroupViewModel::class.java)
        groupListAdapter = GroupNameAdapter(this)
        binding.recyclerView2.layoutManager = LinearLayoutManager(this).also {
            it.orientation = HORIZONTAL
        }
        binding.recyclerView2.adapter = groupListAdapter

        groupListViewModel.foodGroupList.observe(this) {
            updateGroupAdapterList()
        }

        groupListAdapter.setGroupClickListener(object: GroupNameAdapter.GroupClickListener{
            override fun onClick(view: View, position: Int, group: Group) {
                groupId = group.id
                updateGroupAdapterList()
                updateFoodAdapterList()
            }
        })

        // foodlist add btn
        binding.foodListAddButton.setOnClickListener {
            val intent = Intent(this@FoodList, AddFoodList::class.java)
            requestActivity.launch(intent)
        }

        foodListAdapter.setPopupButtonClickListener(object: FoodListAdapter.FoodListClickListener{
            override fun onClick(view: View, position: Int, item: Food) {
                val popupMenu = PopupMenu(this@FoodList, view)
                popupMenu.menuInflater.inflate(R.menu.popup_food, popupMenu.menu)
                popupMenu.show()
                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.delete_menu -> {
                            CoroutineScope(Dispatchers.IO).launch {
                                foodListViewModel.deleteFood(item.id)
                            }
                        }
                        R.id.edit_menu -> {
                            val intent = Intent(this@FoodList, EditFoodList::class.java).apply {
                                putExtra("food", item)
                            }
                            requestActivity.launch(intent)
                        }
                    }
                    true
                }
            }
        })
    }

    private val requestActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            // type 0: add, 1: edit, 2: delete
            when(it.data?.getIntExtra("type", -1)) {
                0 -> {
                    // AddFoodListActivity에서 받은 정보를 이용해 food를 생성해 db에 추가
                    // TODO("코드 리팩토링 필요해 보임")
                    val searchResult = it.data?.getParcelableExtra<Search>("searchResult")
                    val tasteGrade = it.data?.getDoubleExtra("taste", 0.0)
                    val cleanGrade = it.data?.getDoubleExtra("clean", 0.0)
                    val kindnessGrade = it.data?.getDoubleExtra("kindness", 0.0)
                    val visited = it.data?.getIntExtra("visited", 0)
                    val memo = it.data?.getStringExtra("memo")
                    val food = Food(foodListViewModel.getFoodListMaxId() + 1, searchResult!!.name, memo!!,
                        searchResult.fullAddress, searchResult.latitude, searchResult.longitude,
                        tasteGrade!!, cleanGrade!!, kindnessGrade!!, visited!!)
                    CoroutineScope(Dispatchers.IO).launch {
                        foodListViewModel.insertFoodList(food)
                    }
                }
                1 -> {
                    // EditFoodListActivity에서 받은 수정된 food를 이용해 db 수정
                    val editFood = it.data?.getParcelableExtra<Food>("food")
                    CoroutineScope(Dispatchers.IO).launch {
                        foodListViewModel.insertFoodList(editFood!!)
                    }
                }
            }
        }
    }

    private fun setFoodListView() {
        foodListViewModel = ViewModelProvider(this).get(FoodListViewModel::class.java)
        foodListViewModel.foodList.observe(this) {
            updateFoodAdapterList()
        }
    }

    private fun updateFoodAdapterList() {
        foodListAdapter.setFoodListData(foodListViewModel.getFoodList(groupId))
        foodListAdapter.notifyDataSetChanged()
        setEmptyRecyclerView()
    }

    private fun updateGroupAdapterList() {
        val groupList = groupListViewModel.getGroupList()
        if (groupList != null) {
            groupListAdapter.setSelectGroup(groupId)
            groupListAdapter.setListData(groupList)
            groupListAdapter.notifyDataSetChanged()
        } else groupListAdapter.setListDataClear()
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

    private fun setEmptyRecyclerView() {
        if (foodListAdapter.itemCount == 0) {
            binding.recyclerView.visibility = View.INVISIBLE
            binding.emptyText.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.emptyText.visibility = View.INVISIBLE
        }
    }
}
