package com.example.wifood.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wifood.R
import com.example.wifood.adapter.GroupAdapter
import com.example.wifood.databinding.ActivityFoodGroupBinding
import com.example.wifood.entity.Group
import com.example.wifood.viewmodel.FoodGroupViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodGroup : AppCompatActivity() {
    lateinit var binding : ActivityFoodGroupBinding
    private lateinit var foodGroupAdapter: GroupAdapter
    lateinit var foodGroupViewModel : FoodGroupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // Drawer를 꺼낼 홈 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정

        supportActionBar?.title = "맛집 그룹"

        // Connecting RecyclerView and Adapter
        foodGroupViewModel = ViewModelProvider(this).get(FoodGroupViewModel::class.java)
        foodGroupAdapter = GroupAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = foodGroupAdapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, 1))

        // Automatically change bindings when data changes
        foodGroupViewModel.foodGroupList.observe(this) {
            foodGroupAdapter.setListData(it)
            foodGroupAdapter.notifyDataSetChanged()
            setEmptyRecyclerView()
        }

        // group add btn
        binding.groupAddButton.setOnClickListener {
            val intent = Intent(this@FoodGroup, EditFoodGroup::class.java).apply {
                putExtra("type", "ADD")
            }
            requestActivity.launch(intent)
        }

        // group del btn
        binding.groupDeleteButton.setOnClickListener {
            val intent = Intent(this@FoodGroup, DeleteFoodGroup::class.java).apply {
                putExtra("groupName", ArrayList(foodGroupAdapter.getGroupNameList()))
                putExtra("groupId", ArrayList(foodGroupAdapter.getGroupIdList()))
                putExtra("groupColor", ArrayList(foodGroupAdapter.getGroupColorList()))
            }
            requestActivity.launch(intent)
        }

        // group edit btn
        foodGroupAdapter.setGroupEditClickListener(object: GroupAdapter.GroupEditClickListener {
            override fun onClick(view: View, position: Int, groupId: Int) {
                CoroutineScope(Dispatchers.IO).launch {
                    val group: Group = foodGroupViewModel.getGroup(position)
                    val intent = Intent(this@FoodGroup, EditFoodGroup::class.java).apply {
                        putExtra("type", "EDIT")
                        putExtra("groupId", group.id)
                        putExtra("groupName", group.name)
                        putExtra("groupColor", group.color)
                    }
                    requestActivity.launch(intent)
                }
            }
        })

        // group go btn
        foodGroupAdapter.setGroupGoClickListener(object: GroupAdapter.GroupGoClickListener {
            override fun onClick(view: View, position: Int, group: Group) {
                val intent = Intent(this@FoodGroup, FoodList::class.java).apply {
                    putExtra("groupName", group.name)
                    putExtra("groupId", group.id)
                }
                startActivity(intent)
            }
        })
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

    private val requestActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            // type 0 : add, 1 : edit, 2 : delete
            when(it.data?.getIntExtra("type", -1)) {
                0 -> {
                    val maxId = foodGroupAdapter.getGroupIdList().maxOrNull() ?: 0
                    // create a group to add using the value received from EditFoodGroup Activity
                    val group = Group(maxId + 1, it.data?.getSerializableExtra("name") as String,
                        it.data?.getSerializableExtra("color") as String)
                    CoroutineScope(Dispatchers.IO).launch {
                        foodGroupViewModel.groupInsert(group)
                    }
                }
                1 -> {
                    // EditFoodGroup에서 받은 수정된 정보들을 이용해 새로운 group을 생성해 수정
                    val group = Group(it.data?.getSerializableExtra("id") as Int, it.data?.getSerializableExtra("name") as String,
                        it.data?.getSerializableExtra("color") as String)
                    CoroutineScope(Dispatchers.IO).launch {
                        foodGroupViewModel.groupInsert(group)
                    }
                }
                2 -> {
                    // DeleteFoodGroup에서 받은 삭제할 id 리스트를 이용해 group 삭제
                    val groupId = it.data?.getIntegerArrayListExtra("id")
                    CoroutineScope(Dispatchers.IO).launch {
                        if (groupId != null)
                            foodGroupViewModel.groupDelete(groupId)
                    }
                }
            }
        }
    }

    private fun setEmptyRecyclerView() {
        if (foodGroupAdapter.itemCount == 0) {
            binding.recyclerView.visibility = View.GONE
            binding.emptyText.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.emptyText.visibility = View.GONE
        }
    }
}
