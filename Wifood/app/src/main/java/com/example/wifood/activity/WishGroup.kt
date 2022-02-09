package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wifood.R
import com.example.wifood.adapter.GroupAdapter
import com.example.wifood.databinding.ActivityWishGroupBinding
import com.example.wifood.entity.Group
import com.example.wifood.viewmodel.WishGroupViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WishGroup : AppCompatActivity() {
    lateinit var binding : ActivityWishGroupBinding
    lateinit var wishGroupViewModel : WishGroupViewModel
    private lateinit var wishGroupAdapter : GroupAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWishGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // Drawer를 꺼낼 홈 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정

        supportActionBar?.title = "위시 그룹"

        wishGroupViewModel = ViewModelProvider(this).get(WishGroupViewModel::class.java)
        wishGroupAdapter = GroupAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = wishGroupAdapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, 1))

        wishGroupViewModel.wishGroupList.observe(this) {
            wishGroupAdapter.setListData(it)
            wishGroupAdapter.notifyDataSetChanged()
        }

        // group add btn
        binding.groupAddButton.setOnClickListener {
            val intent = Intent(this@WishGroup, EditFoodGroup::class.java).apply {
                putExtra("type", "ADD")
            }
            requestActivity.launch(intent)
        }

        // group del btn
        binding.groupDeleteButton.setOnClickListener {
            val intent = Intent(this@WishGroup, DeleteFoodGroup::class.java).apply {
                putExtra("groupName", ArrayList(wishGroupAdapter.getGroupNameList()))
                putExtra("groupId", ArrayList(wishGroupAdapter.getGroupIdList()))
                putExtra("groupColor", ArrayList(wishGroupAdapter.getGroupColorList()))
            }
            requestActivity.launch(intent)
        }

        // group edit btn
        wishGroupAdapter.setGroupEditClickListener(object: GroupAdapter.GroupEditClickListener {
            override fun onClick(view: View, position: Int, groupId: Int) {
                CoroutineScope(Dispatchers.IO).launch {
                    val group: Group = wishGroupViewModel.getGroup(position)
                    val intent = Intent(this@WishGroup, EditFoodGroup::class.java).apply {
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
        wishGroupAdapter.setGroupGoClickListener(object: GroupAdapter.GroupGoClickListener {
            override fun onClick(view: View, position: Int, groupId: Int) {
                CoroutineScope(Dispatchers.IO).launch {
                    val intent = Intent(this@WishGroup, WishList::class.java).apply { putExtra("groupId", groupId) }
                    startActivity(intent)
                }
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
                    val maxId = wishGroupAdapter.getGroupIdList().maxOrNull() ?: 0
                    // create a group to add using the value received from EditFoodGroup Activity
                    val group = Group(maxId + 1, it.data?.getSerializableExtra("name") as String,
                        it.data?.getSerializableExtra("color") as String)
                    CoroutineScope(Dispatchers.IO).launch {
                        wishGroupViewModel.groupInsert(group)
                    }
                }
                1 -> {
                    val group = Group(it.data?.getSerializableExtra("id") as Int, it.data?.getSerializableExtra("name") as String,
                        it.data?.getSerializableExtra("color") as String)
                    CoroutineScope(Dispatchers.IO).launch {
                        wishGroupViewModel.groupInsert(group)
                    }
                }
                2 -> {
                    val groupId = it.data?.getIntegerArrayListExtra("id")
                    CoroutineScope(Dispatchers.IO).launch {
                        if (groupId != null)
                            wishGroupViewModel.groupDelete(groupId)
                    }
                }
            }
        }
    }
}