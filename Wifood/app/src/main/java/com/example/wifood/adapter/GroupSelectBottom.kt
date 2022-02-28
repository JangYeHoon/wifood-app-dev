package com.example.wifood.adapter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wifood.R
import com.example.wifood.activity.AddFoodList
import com.example.wifood.entity.Group
import com.example.wifood.viewmodel.FoodGroupViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class GroupSelectBottom : BottomSheetDialogFragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var foodGroupAdapter: GroupSelectAdapter
    val foodGroupViewModel: FoodGroupViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_group_select_bottom, container, false)
        foodGroupAdapter = GroupSelectAdapter(requireContext())
        recyclerView = rootView.findViewById(R.id.groupRecycler) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = foodGroupAdapter
        foodGroupViewModel.foodGroupList.observe(viewLifecycleOwner) {
            if (it != null) foodGroupAdapter.setListData(it)
            else foodGroupAdapter.setListDataClear()
            foodGroupAdapter.notifyDataSetChanged()
        }

        foodGroupAdapter.setGroupClickListener(object: GroupSelectAdapter.GroupClickListener {
            override fun onClick(view: View, position: Int, group: Group) {
                val addFood = activity as AddFoodList
                addFood.receiveData(group)
                dismiss()
            }
        })
        return rootView
    }
}