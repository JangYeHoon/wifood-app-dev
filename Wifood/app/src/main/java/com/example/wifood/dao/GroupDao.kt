package com.example.wifood.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.domain.entity.Group
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*

class GroupDao() {
    private var groupDatabase: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Group")

    // 디비에서 맛집/위시 그룹 정보 받아옴
    fun getGroupList(): LiveData<MutableList<Group>> {
        val groupList = MutableLiveData<MutableList<Group>>()
        groupDatabase.addValueEventListener(object : ValueEventListener {
            // Called only when there is a data change
            override fun onDataChange(snapshot: DataSnapshot) {
                val dbList: MutableList<Group> = mutableListOf()
                if (snapshot.exists()) {
                    for (groupSnapshot in snapshot.children) {
                        val group = groupSnapshot.getValue(Group::class.java)
                        dbList.add(group!!)
                        groupList.value = dbList
                    }
                } else groupList.postValue(null)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return groupList
    }

    fun getGroupById(groupId: Int): Task<DataSnapshot> {
        return groupDatabase.child(groupId.toString()).get()
    }

    fun insertGroup(group: Group) {
        // create table using id and add data
        groupDatabase.child(group.id.toString()).setValue(group)
    }

    // 그룹 id에 해당하는 정보 디비에서 삭제
    fun deleteGroup(groupId: Int) {
        groupDatabase.child(groupId.toString()).removeValue()
    }

    fun updateGroup(group: Group) {
        groupDatabase.child(group.id.toString()).child("order").setValue(group.order)
        groupDatabase.child(group.id.toString()).child("name").setValue(group.name)
        groupDatabase.child(group.id.toString()).child("theme").setValue(group.theme)
        groupDatabase.child(group.id.toString()).child("color").setValue(group.color)
    }
}