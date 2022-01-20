package com.example.wifood.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.entity.Group
import com.google.firebase.database.*

class FoodGroupDao (private val foodGroupDatabase: DatabaseReference = FirebaseDatabase.getInstance().getReference("FoodGroup")){
    fun getGroupList() : LiveData<MutableList<Group>> {
        val foodGroupList = MutableLiveData<MutableList<Group>>()
        foodGroupDatabase.addValueEventListener(object: ValueEventListener {
            val groupList : MutableList<Group> = mutableListOf()
            // Called only when there is a data change
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (foodGroupSnapshot in snapshot.children) {
                        val getFoodGroup = foodGroupSnapshot.getValue(Group::class.java)
                        groupList.add(getFoodGroup!!)
                        foodGroupList.value = groupList
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return foodGroupList
    }
}