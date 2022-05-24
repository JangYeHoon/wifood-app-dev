package com.example.wifood.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class WifoodApiImpl @Inject constructor(
    private val db: DatabaseReference
): WifoodApi {
    override fun getGroupList(): LiveData<MutableList<Group>> {
        val groupList = MutableLiveData<MutableList<Group>>()
        db.child("Group").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list: MutableList<Group> = mutableListOf()
                if (snapshot.exists()) {
                    for (foodGroupSnapshot in snapshot.children) {
                        val group = foodGroupSnapshot.getValue(Group::class.java)
                        list.add(group!!)
                        groupList.value = list
                    }
                } else groupList.postValue(null)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return groupList
    }

    override fun getPlaceList(): LiveData<MutableList<Place>> {
        val placeList = MutableLiveData<MutableList<Place>>()
        db.child("Place").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list: MutableList<Place> = mutableListOf()
                if (snapshot.exists()) {
                    for (PlaceSnapshot in snapshot.children) {
                        val place = PlaceSnapshot.getValue(Place::class.java)
                        list.add(place!!)
                        placeList.value = list
                    }
                } else placeList.postValue(null)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return placeList
    }
}