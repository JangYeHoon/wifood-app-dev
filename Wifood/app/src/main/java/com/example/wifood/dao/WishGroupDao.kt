package com.example.wifood.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.entity.Group
import com.google.firebase.database.*

class WishGroupDao (private val wishGroupDatabase: DatabaseReference = FirebaseDatabase.getInstance().getReference("WishGroup")){
    fun getGroupList() : LiveData<MutableList<Group>> {
        val wishGroupList = MutableLiveData<MutableList<Group>>()
        wishGroupDatabase.addValueEventListener(object: ValueEventListener {
            // Called only when there is a data change
            override fun onDataChange(snapshot: DataSnapshot) {
                val groupList : MutableList<Group> = mutableListOf()
                if (snapshot.exists()) {
                    for (foodGroupSnapshot in snapshot.children) {
                        val getFoodGroup = foodGroupSnapshot.getValue(Group::class.java)
                        groupList.add(getFoodGroup!!)
                        wishGroupList.value = groupList
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return wishGroupList
    }

    fun wishGroupInsert(group: Group) {
        // create table using id and add data
        wishGroupDatabase.child(group.id.toString()).setValue(group)
    }

    fun wishGroupDelete(groupId : Int) {
        wishGroupDatabase.child(groupId.toString()).removeValue()
    }
}