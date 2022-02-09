package com.example.wifood.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wifood.dto.FoodListDto
import com.example.wifood.entity.Food

class FoodListViewModel(groupId : Int): ViewModel() {
    var foodList: LiveData<MutableList<Food>>
    private val foodListDto: FoodListDto = FoodListDto(groupId)

    init {
        foodList = foodListDto.getFoodList()
    }

    fun getFoodListMaxId() : Int {
        val food = foodList.value
        var maxValue = 0
        if (food != null)
            for (f in food)
                maxValue = Integer.max(f.id, maxValue)
        return maxValue
    }

    fun insertFoodList(food: Food) {
        foodListDto.insertFoodList(food)
    }

    fun deleteFoodList(foodIdList: ArrayList<Int>) {
        foodListDto.deleteFoodList(foodIdList)
    }

    class Factory(val groupId : Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FoodListViewModel(groupId) as T
        }
    }
}