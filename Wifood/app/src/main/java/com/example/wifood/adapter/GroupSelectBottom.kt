package com.example.wifood.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wifood.R
import com.example.wifood.activity.AddPlace
import com.example.wifood.entity.Group
import com.example.wifood.viewmodel.GroupViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class GroupSelectBottom : BottomSheetDialogFragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var groupSelectAdapter: GroupSelectAdapter
    private val groupViewModel: GroupViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_group_select_bottom, container, false)
        groupSelectAdapter = GroupSelectAdapter(requireContext())
        recyclerView = rootView.findViewById(R.id.recyclerView_groupList) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = groupSelectAdapter
        groupViewModel.groupList.observe(viewLifecycleOwner) {
            if (it != null) groupSelectAdapter.setListData(it)
            else groupSelectAdapter.setListDataClear()
            groupSelectAdapter.notifyDataSetChanged()
        }

        groupSelectAdapter.setGroupClickListener(object : GroupSelectAdapter.GroupClickListener {
            override fun onClick(view: View, position: Int, group: Group) {
                val addPlace = activity as AddPlace
                addPlace.setGroupByGroupEntity(group)
                dismiss()
            }
        })
        return rootView
    }
}