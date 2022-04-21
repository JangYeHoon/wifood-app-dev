package com.example.wifood.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wifood.R
import com.example.wifood.adapter.GroupListAdapter
import com.example.wifood.adapter.GroupItemTouchHelperCallback
import com.example.wifood.databinding.ActivityGroupListBinding
import com.example.wifood.entity.Group
import com.example.wifood.viewmodel.GroupListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroupListView : AppCompatActivity() {
    lateinit var binding: ActivityGroupListBinding
    private lateinit var groupListAdapter: GroupListAdapter
    private lateinit var groupListViewModel: GroupListViewModel
    var checkTouch = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // Drawer를 꺼낼 홈 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정
        supportActionBar?.title = "맛집 그룹"

        // Connecting RecyclerView and Adapter
        groupListViewModel = ViewModelProvider(this).get(GroupListViewModel::class.java)
        groupListAdapter = GroupListAdapter(this)
        binding.recyclerViewGroupList.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewGroupList.adapter = groupListAdapter
        binding.recyclerViewGroupList.addItemDecoration(DividerItemDecoration(this, 1))
        val groupItemTouchHelperCallback = GroupItemTouchHelperCallback(groupListAdapter)
        val groupTouchHelper = ItemTouchHelper(groupItemTouchHelperCallback)
        groupTouchHelper.attachToRecyclerView(binding.recyclerViewGroupList)

        // Automatically change bindings when data changes
        groupListViewModel.groupList.observe(this) {
            if (it != null) groupListAdapter.setListData(it)
            else groupListAdapter.setListDataClear()
            groupListAdapter.notifyDataSetChanged()
            setEmptyRecyclerView()
        }

        // 그룹 순서 변경
        groupListAdapter.setOnStartDragListener(object : GroupListAdapter.OnStartDragListener {
            override fun onStartDrag(viewHolder: GroupListAdapter.GroupViewHolder) {
                groupTouchHelper.startDrag(viewHolder)
                checkTouch = true
            }
        })

        // group add btn
        binding.imageButtonGroupInsert.setOnClickListener {
            val intent = Intent(this@GroupListView, EditGroupView::class.java).apply {
                putExtra("type", "ADD")
                putExtra("groupId", groupListViewModel.getGroupMaxId() + 1)
            }
            requestActivity.launch(intent)
        }

        // group del btn
//        binding.groupDeleteButton.setOnClickListener {
//            val intent = Intent(this@GroupList, DeleteGroup::class.java).apply {
//                putExtra("groupName", ArrayList(groupListAdapter.getGroupNameList()))
//                putExtra("groupId", ArrayList(groupListAdapter.getGroupIdList()))
//                putExtra("groupColor", ArrayList(groupListAdapter.getGroupColorList()))
//            }
//            requestActivity.launch(intent)
//        }

        // group edit btn
        groupListAdapter.setGroupEditClickListener(object :
            GroupListAdapter.GroupEditClickListener {
            override fun onClick(view: View, position: Int, group: Group) {
                CoroutineScope(Dispatchers.IO).launch {
                    val intent =
                        Intent(this@GroupListView, EditGroupComposeView::class.java).apply {
                            putExtra("type", "EDIT")
                            putExtra("group", group)
                        }
                    requestActivity.launch(intent)
                }
            }
        })

        // group go btn
        groupListAdapter.setGroupGoClickListener(object :
            GroupListAdapter.GroupGoClickListener {
            override fun onClick(view: View, position: Int, group: Group) {
                val intent = Intent(this@GroupListView, PlaceListView::class.java).apply {
                    putExtra("groupName", group.name)
                    putExtra("groupId", group.id)
                }
                startActivity(intent)
            }
        })
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev!!.action) {
            MotionEvent.ACTION_UP -> {
                if (checkTouch) {
                    groupListViewModel.setGroupOrderByGroupList(groupListAdapter.getListData())
                    checkTouch = false
                }
            }
        }
        return super.dispatchTouchEvent(ev)
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

    private val requestActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                // type 0 : add, 1 : edit, 2 : delete
                when (it.data?.getIntExtra("type", -1)) {
                    0 -> {
                        val group = it.data?.getParcelableExtra<Group>("group")
                        CoroutineScope(Dispatchers.IO).launch {
                            groupListViewModel.insertGroup(group!!)
                        }
                    }
                    1 -> {
                        // EditPlaceGroup에서 받은 수정된 정보들을 이용해 새로운 group을 생성해 수정
                        val group = it.data?.getParcelableExtra<Group>("group")
                        CoroutineScope(Dispatchers.IO).launch {
                            groupListViewModel.updateGroup(group!!)
                        }
                    }
                    2 -> {
                        // DeletePlaceGroup에서 받은 삭제할 id 리스트를 이용해 group 삭제
                        val groupId = it.data?.getIntegerArrayListExtra("id")
                        CoroutineScope(Dispatchers.IO).launch {
                            if (groupId != null)
                                groupListViewModel.deleteGroup(groupId)
                        }
                    }
                }
            }
        }

    private fun setEmptyRecyclerView() {
        if (groupListAdapter.itemCount == 0) {
            binding.recyclerViewGroupList.visibility = View.INVISIBLE
            binding.emptyText.visibility = View.VISIBLE
        } else {
            binding.recyclerViewGroupList.visibility = View.VISIBLE
            binding.emptyText.visibility = View.INVISIBLE
        }
    }
}