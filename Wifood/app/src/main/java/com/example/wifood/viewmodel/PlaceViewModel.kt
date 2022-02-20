package com.example.wifood.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.wifood.dto.PlaceDto
import com.example.wifood.entity.Place

class PlaceViewModel: ViewModel() {
    var placeList: LiveData<MutableList<Place>>
    private val placeDto = PlaceDto()

    init {
        placeList = placeDto.getPlaceList()
    }
}