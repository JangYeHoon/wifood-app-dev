package com.example.wifood.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.entity.Food
import com.google.firebase.database.*

class FoodListDao (groupId : Int) {
    private var databaseFoodList: DatabaseReference = FirebaseDatabase.getInstance().getReference("FoodGroup/$groupId/foodlist")

    // 디비에서 foodlist 정보 받아옴
    fun getFoodList() : LiveData<MutableList<Food>> {
        val foodList = MutableLiveData<MutableList<Food>>()
        databaseFoodList.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dbList : MutableList<Food> = mutableListOf()
                if (snapshot.exists()) {
                    for (snapshotFoodList in snapshot.children) {
                        val food = snapshotFoodList.getValue(Food::class.java)
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
    fun insertFoodList(food: Food) {
        databaseFoodList.child(food.id.toString()).setValue(food)
    }

    // 디비에서 해당 id를 가진 food 삭제
    fun deleteFoodList(foodId: Int) {
        databaseFoodList.child(foodId.toString()).removeValue()
    }
}