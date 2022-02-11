package com.example.wifood.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.entity.Group
import com.google.firebase.database.*

class GroupDao (groupType: String){
    private var groupDatabase: DatabaseReference =
        if (groupType == "food") FirebaseDatabase.getInstance().getReference("FoodGroup")
        else FirebaseDatabase.getInstance().getReference("WishGroup")

    // 디비에서 맛집/위시 그룹 정보 받아옴
    fun getGroupList() : LiveData<MutableList<Group>> {
        val groupList = MutableLiveData<MutableList<Group>>()
        groupDatabase.addValueEventListener(object: ValueEventListener {
            // Called only when there is a data change
            override fun onDataChange(snapshot: DataSnapshot) {
                val dbList : MutableList<Group> = mutableListOf()
                if (snapshot.exists()) {
                    for (foodGroupSnapshot in snapshot.children) {
                        val group = foodGroupSnapshot.getValue(Group::class.java)
                        dbList.add(group!!)
                        groupList.value = dbList
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return groupList
    }

    fun foodGroupInsert(group: Group) {
        // create table using id and add data
        groupDatabase.child(group.id.toString()).setValue(group)
    }

    // 그룹 id에 해당하는 정보 디비에서 삭제
    fun foodGroupDelete(groupId : Int) {
        groupDatabase.child(groupId.toString()).removeValue()
    }
}