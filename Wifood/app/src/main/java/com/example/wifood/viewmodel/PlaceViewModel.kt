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

    fun insertPlace(place:Place) {
        placeDto.insertPlace(place)
    }

    fun getPlaceId(name:String): Int {
        for (place in placeList.value!!) {
            if (place.name == name)
                return place.id
        }
        return 0
    }

    fun getPlaceMaxId(): Int {
        var maxValue = 0
        for (place in placeList.value!!)
            maxValue = Integer.max(place.id, maxValue)
        return maxValue
    }

    fun setPlaceGrade(placeId:Int, taste:Double, clean:Double, kindness:Double) {
        for (place in placeList.value!!) {
            if (place.id == placeId) {
                place.tasteGrade += taste
                place.cleanGrade += clean
                place.kindnessGrade += kindness
                break
            }
        }
    }

    fun getPlaceGrade(placeId:Int):DoubleArray {
        var arr = DoubleArray(3) { 0.0 }
        for (place in placeList.value!!) {
            if (place.id == placeId) {
                arr[0] = place.tasteGrade
                arr[1] = place.cleanGrade
                arr[2] = place.tasteGrade
                break
            }
        }
        return arr
    }

    fun setPlaceVisit(placeId:Int) {
        for (place in placeList.value!!) {
            if (place.id == placeId)
                place.personCount++
        }
    }

    fun getPlaceVisit(placeId:Int):Int {
        for (place in placeList.value!!) {
            if (place.id == placeId)
                return place.personCount
        }
        return 1
    }
}