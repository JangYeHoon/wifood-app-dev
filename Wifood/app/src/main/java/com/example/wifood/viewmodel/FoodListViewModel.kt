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

    class Factory(val groupId : Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FoodListViewModel(groupId) as T
        }
    }
}