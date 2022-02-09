package com.example.wifood.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.entity.Food
import com.google.firebase.database.*

class FoodListDao (groupId : Int) {
    private var databaseFoodList: DatabaseReference = FirebaseDatabase.getInstance().getReference("FoodGroup/$groupId/foodlist")

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
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return foodList
    }

    fun insertFoodList(food: Food) {
        databaseFoodList.child(food.id.toString()).setValue(food)
    }

    fun deleteFoodList(foodId: Int) {
        databaseFoodList.child(foodId.toString()).removeValue()
    }
}