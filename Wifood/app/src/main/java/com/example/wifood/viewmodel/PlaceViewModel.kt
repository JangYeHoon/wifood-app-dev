package com.example.wifood.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.wifood.dto.PlaceDto
import com.example.wifood.entity.Place

class PlaceViewModel() : ViewModel() {
    var placeList: LiveData<MutableList<Place>>
    private val placeDto: PlaceDto = PlaceDto()

    init {
        placeList = placeDto.getPlaceList()
    }

    fun getPlaceListMaxId(): Int {
        val food = placeList.value
        var maxValue = 0
        if (food != null)
            for (f in food)
                maxValue = Integer.max(f.id, maxValue)
        return maxValue
    }

    fun getPlaceListByGroupId(groupId: Int): MutableList<Place> {
        val food = mutableListOf<Place>()
        if (groupId != -1) {
            for (f in placeList.value!!) {
                if (groupId == f.groupId)
                    food.add(f)
            }
            return food
        }
        return placeList.value!!
    }

    fun insertPlace(place: Place) {
        placeDto.insertPlaceList(place)
    }

    fun updatePlace(place: Place) {
        placeDto.insertPlaceList(place)
    }

    fun deletePlace(foodId: Int) {
        placeDto.deletePlaceList(foodId)
    }
}