package com.example.wifood.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.data.remote.dto.GroupDto
import com.example.wifood.data.remote.dto.PlaceDto
import com.example.wifood.data.remote.dto.TasteDto
import com.example.wifood.data.remote.dto.UserDto
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WifoodApiImpl @Inject constructor(
    private val db: DatabaseReference
) : WifoodApi {
    override fun getGroupList(): LiveData<MutableList<GroupDto>> {
        val groupList = MutableLiveData<MutableList<GroupDto>>()
        db.child("1").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list: MutableList<GroupDto> = mutableListOf()
                if (snapshot.exists()) {
                    for (foodGroupSnapshot in snapshot.children) {
                        val group = foodGroupSnapshot.getValue(GroupDto::class.java)
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

    override suspend fun getAll(userId: String) {
        Log.d("TEST", "API launched")
        db.child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val placeList = mutableListOf<PlaceDto>()
                Log.d("TEST", "Firebase connected")
                val user = snapshot.getValue(UserDto::class.java)
                val groupList =
                    snapshot.child("Group").children.map {
                        it.getValue(GroupDto::class.java)
                        it.child("Place").children.map { place ->
                            place.getValue(PlaceDto::class.java)
                        }
                    }
                val taste = snapshot.child("Taste").getValue(TasteDto::class.java)
                Log.d("TEST", user.toString())
                Log.d("TEST", groupList.toString())
                Log.d("TEST", taste.toString())
                Log.d("TEST", placeList.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("TEST", "Firebase cancelled")
            }
        })
    }
}