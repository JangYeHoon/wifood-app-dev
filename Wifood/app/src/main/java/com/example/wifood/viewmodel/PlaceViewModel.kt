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
        val place = placeList.value
        var maxValue = 0
        if (place != null)
            for (f in place)
                maxValue = Integer.max(f.id, maxValue)
        return maxValue
    }

    fun getPlaceListByGroupId(groupId: Int): MutableList<Place>? {
        val place = mutableListOf<Place>()
        if (groupId != -1 && placeList.value != null) {
            for (f in placeList.value!!) {
                if (groupId == f.groupId)
                    place.add(f)
            }
            return place
        }
        return placeList.value
    }

    fun insertPlace(place: Place) {
        placeDto.insertPlaceList(place)
    }

    fun updatePlace(place: Place) {
        placeDto.insertPlaceList(place)
    }

    fun deletePlace(placeId: Int) {
        placeDto.deletePlaceList(placeId)
    }
}