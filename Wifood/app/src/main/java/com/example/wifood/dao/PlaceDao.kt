package com.example.wifood.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.entity.Place
import com.google.firebase.database.*

class PlaceDao() {
    private var databasePlaceList: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("FoodList")

    // 디비에서 foodlist 정보 받아옴
    fun getPlaceList(): LiveData<MutableList<Place>> {
        val foodList = MutableLiveData<MutableList<Place>>()
        databasePlaceList.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dbList: MutableList<Place> = mutableListOf()
                if (snapshot.exists()) {
                    for (snapshotFoodList in snapshot.children) {
                        val food = snapshotFoodList.getValue(Place::class.java)
                        dbList.add(food!!)
                        foodList.value = dbList
                    }
                } else foodList.postValue(null)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return foodList
    }

    // 디비에 해당 food 정보 추가 or 수정
    fun insertPlaceList(place: Place) {
        databasePlaceList.child(place.id.toString()).setValue(place)
    }

    // 디비에서 해당 id를 가진 food 삭제
    fun deletePlaceList(foodId: Int) {
        databasePlaceList.child(foodId.toString()).removeValue()
    }
}