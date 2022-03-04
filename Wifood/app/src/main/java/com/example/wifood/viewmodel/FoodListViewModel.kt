package com.example.wifood.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.wifood.dto.FoodListDto
import com.example.wifood.entity.Food

class FoodListViewModel(): ViewModel() {
    var foodList: LiveData<MutableList<Food>>
    private val foodListDto: FoodListDto = FoodListDto()

    init {
        foodList = foodListDto.getFoodList()
    }

    private fun getFoodListMaxId() : Int {
        val food = foodList.value
        var maxValue = 0
        if (food != null)
            for (f in food)
                maxValue = Integer.max(f.id, maxValue)
        return maxValue
    }

    fun getFoodList(groupId: Int): MutableList<Food> {
        val food = mutableListOf<Food>()
        if (groupId != -1) {
            for (f in foodList.value!!) {
                if (groupId == f.groupId)
                    food.add(f)
            }
            return food
        }
        return foodList.value!!
    }

    fun insertFoodList(food: Food) {
        food.id = getFoodListMaxId() + 1
        foodListDto.insertFoodList(food)
    }

    fun updateFoodList(food: Food) {
        foodListDto.insertFoodList(food)
    }

    fun deleteFood(foodId: Int) {
        foodListDto.deleteFoodList(foodId)
    }
}