package com.example.wifood.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.example.wifood.R
import com.example.wifood.adapter.PlaceListAdapter
import com.example.wifood.adapter.GroupNameAdapter
import com.example.wifood.databinding.ActivityPlaceListBinding
import com.example.wifood.domain.entity.Place
import com.example.wifood.domain.entity.Group
import com.example.wifood.presentation.viewmodel.GroupListViewModel
import com.example.wifood.presentation.viewmodel.PlaceListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaceListView : AppCompatActivity() {
    lateinit var binding: ActivityPlaceListBinding
    private lateinit var placeListAdapter: PlaceListAdapter
    lateinit var placeListViewModel: PlaceListViewModel
    private lateinit var groupListAdapter: GroupNameAdapter
    lateinit var groupListListViewModel: GroupListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 설정
        val toolbar: Toolbar = findViewById(R.id.main_layout_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)                       // 뒤로가기 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(true)                      // 툴바에 타이틀 안보이게 설정
        supportActionBar?.title = intent.getStringExtra("groupName")

        // 데이터베이스에서 받아온 placelist 정보를 이용해 recyclerView 설정
        placeListAdapter = PlaceListAdapter(this)
        binding.recyclerViewPlaceList.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewPlaceList.adapter = placeListAdapter
        binding.recyclerViewPlaceList.addItemDecoration(DividerItemDecoration(this, 1))
        setPlaceListView()

        groupListAdapter = GroupNameAdapter(this)
        binding.recyclerViewGroupList.layoutManager = LinearLayoutManager(this).also {
            it.orientation = HORIZONTAL
        }
        val spaceDecoration = VerticalSpaceItemDecoration(10)
        binding.recyclerViewGroupList.addItemDecoration(spaceDecoration)
        binding.recyclerViewGroupList.adapter = groupListAdapter

        groupListListViewModel = ViewModelProvider(this).get(GroupListViewModel::class.java)
        // 데이터베이스 접근을 위한 place group id정보 받아옴
        groupListListViewModel.setSelectGroupId(intent.getIntExtra("groupId", 0))

        groupListListViewModel.groupList.observe(this) {
            updateGroupAdapterList()
            updatePlaceAdapterList()
            setGroupAllButtonColorBySelect(false)
            binding.recyclerViewGroupList.smoothScrollToPosition(groupListAdapter.getGroupPosition())
        }

        groupListAdapter.setGroupClickListener(object : GroupNameAdapter.GroupClickListener {
            override fun onClick(view: View, position: Int, group: Group) {
                groupListListViewModel.setSelectGroupId(group.id)
                updateGroupAdapterList()
                updatePlaceAdapterList()
                setGroupAllButtonColorBySelect(false)
            }
        })

        binding.imageButtonGroupInsert.setOnClickListener {
            val intent = Intent(this@PlaceListView, EditGroupView::class.java).apply {
                putExtra("type", "ADD")
                putExtra("groupId", groupListListViewModel.getGroupMaxId() + 1)
            }
            requestGroupActivity.launch(intent)
        }

        // place add btn
        binding.imageButtonPlaceInsert.setOnClickListener {
            val intent = Intent(this@PlaceListView, EditPlaceView::class.java).apply {
                putExtra("groupId", groupListListViewModel.getSelectGroupId())
                putExtra("groupName", groupListListViewModel.getSelectGroupName())
                putExtra("placeId", placeListViewModel.getPlaceListMaxId() + 1)
                putExtra("type", "add")
            }
            requestActivity.launch(intent)
        }

        placeListAdapter.setPlaceListClickListener(object :
            PlaceListAdapter.PlaceListClickListener {
            override fun onClick(view: View, position: Int, item: Place) {
                val intent = Intent(this@PlaceListView, PlaceInfoView::class.java).apply {
                    putExtra("place", item)
                    putExtra("groupName", groupListListViewModel.getSelectGroupName())
                }
                startActivity(intent)
            }
        })

        placeListAdapter.setPopupButtonClickListener(object :
            PlaceListAdapter.PlaceListClickListener {
            override fun onClick(view: View, position: Int, item: Place) {
                val popupMenu = PopupMenu(this@PlaceListView, view)
                popupMenu.menuInflater.inflate(R.menu.popup_place_menu, popupMenu.menu)
                popupMenu.show()
                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.delete_menu -> {
                            CoroutineScope(Dispatchers.IO).launch {
                                placeListViewModel.deletePlace(item.id)
                            }
                        }
                        R.id.edit_menu -> {
                            val intent =
                                Intent(this@PlaceListView, EditPlaceView::class.java).apply {
                                    putExtra("place", item)
                                    putExtra(
                                        "groupName",
                                        groupListListViewModel.getSelectGroupName()
                                    )
                                    putExtra("type", "edit")
                                }
                            requestActivity.launch(intent)
                        }
                    }
                    true
                }
            }
        })

        binding.textViewGroupAll.setOnClickListener {
            groupListListViewModel.setSelectGroupId(-1)
            updateGroupAdapterList()
            updatePlaceAdapterList()
            setGroupAllButtonColorBySelect(true)
        }
    }

    private val requestActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val place = it.data?.getParcelableExtra<Place>("place")
                // type 0: add, 1: edit, 2: delete
                when (it.data?.getIntExtra("type", -1)) {
                    0 -> {
                        // AddPlaceListActivity에서 받은 정보를 이용해 Place를 생성해 db에 추가
                        CoroutineScope(Dispatchers.IO).launch {
                            placeListViewModel.insertPlace(place!!)
                        }
                    }
                    1 -> {
                        // EditPlaceListActivity에서 받은 수정된 place를 이용해 db 수정
                        CoroutineScope(Dispatchers.IO).launch {
                            placeListViewModel.updatePlace(place!!)
                        }
                    }
                }
                groupListListViewModel.setSelectGroupId(place!!.groupId)
                updateGroupAdapterList()
                binding.recyclerViewGroupList.smoothScrollToPosition(groupListAdapter.getGroupPosition())
                setGroupAllButtonColorBySelect(false)
            }
        }

    private val requestGroupActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                // type 0 : add, 1 : edit, 2 : delete
                when (it.data?.getIntExtra("type", -1)) {
                    0 -> {
                        val group = it.data?.getParcelableExtra<Group>("group")
                        CoroutineScope(Dispatchers.IO).launch {
                            groupListListViewModel.insertGroup(group!!)
                            groupListListViewModel.setSelectGroupId(group.id)
                        }
                    }
                }
            }
        }

    private fun setPlaceListView() {
        placeListViewModel = ViewModelProvider(this).get(PlaceListViewModel::class.java)
        placeListViewModel.placeList.observe(this) {
            updatePlaceAdapterList()
        }
    }

    private fun updatePlaceAdapterList() {
        placeListViewModel.getPlaceListByGroupId(groupListListViewModel.getSelectGroupId())
            ?.let { placeListAdapter.setPlaceListData(it) }
        placeListAdapter.notifyDataSetChanged()
        setEmptyRecyclerView()
    }

    private fun updateGroupAdapterList() {
        val groupList = groupListListViewModel.getGroupList()
        if (groupList != null) {
            groupListAdapter.setSelectGroupByGroupId(groupListListViewModel.getSelectGroupId())
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

    private fun setGroupAllButtonColorBySelect(isSelect: Boolean) {
        if (isSelect) {
            binding.textViewGroupAll.background =
                ContextCompat.getDrawable(this, R.drawable.bg_rounding_box_check)
            binding.textViewGroupAll.setTextColor(Color.WHITE)
        } else {
            binding.textViewGroupAll.background =
                ContextCompat.getDrawable(this, R.drawable.bg_rounding_box)
            binding.textViewGroupAll.setTextColor(Color.BLACK)
        }
    }

    private fun setEmptyRecyclerView() {
        if (placeListAdapter.itemCount == 0) {
            binding.recyclerViewPlaceList.visibility = View.INVISIBLE
            binding.textViewEmptyText.visibility = View.VISIBLE
        } else {
            binding.recyclerViewPlaceList.visibility = View.VISIBLE
            binding.textViewEmptyText.visibility = View.INVISIBLE
        }
    }

    inner class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) :
        RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.right = verticalSpaceHeight
        }
    }
}
