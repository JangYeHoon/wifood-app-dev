package com.example.wifood.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.entity.Place
import com.google.firebase.database.*

class PlaceDao {
    private var databasePlace: DatabaseReference = FirebaseDatabase.getInstance().getReference("Place")

    fun getPlaceList() : LiveData<MutableList<Place>> {
        val placeList = MutableLiveData<MutableList<Place>>()
        databasePlace.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dbList : MutableList<Place> = mutableListOf()
                if (snapshot.exists()) {
                    for (snapshotPlaceList in snapshot.children) {
                        val place = snapshotPlaceList.getValue(Place::class.java)
                        dbList.add(place!!)
                        placeList.value = dbList
                    }
                } else placeList.postValue(null)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return placeList
    }

    fun insertPlace(place:Place) {
        databasePlace.child(place.id.toString()).setValue(place)
    }
}