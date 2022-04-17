package com.example.wifood.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.wifood.dto.PlaceDto
import com.example.wifood.entity.MenuGrade
import com.example.wifood.entity.Place

class PlaceViewModel : ViewModel() {
    private val placeDto: PlaceDto = PlaceDto()
    lateinit var place: Place

    fun initPlace(place: Place) {
        this.place = place
    }

    fun getPlaceInstance(): Place {
        return place
    }

    fun getPlaceName(): String {
        return place.name
    }

    fun getPlaceAddress(): String {
        return place.address
    }

    fun getPlaceMemo(): String {
        return place.memo
    }

    fun getTasteGrade(): Double {
        return place.myTasteGrade
    }

    fun getKindnessGrade(): Double {
        return place.myKindnessGrade
    }

    fun getCleanGrade(): Double {
        return place.myCleanGrade
    }

    fun getMenuListToString(): String {
        var ret = ""
        for (i in 0 until place.menu.size) {
            ret += place.menu[i].name
            if (i != place.menu.size - 1)
                ret += ","
        }
        return ret
    }

    fun getMenuGradeList(): ArrayList<MenuGrade> {
        return place.menuGrade
    }

    fun getPlaceLatitude(): Double {
        return place.latitude
    }

    fun getPlaceLongitude(): Double {
        return place.longitude
    }

    fun getImageUri(): String {
        return place.imageUri[0]
    }

    fun getPlaceId(): Int {
        return place.id
    }

    fun getPlaceGroupId(): Int {
        return place.groupId
    }

    fun isVisited(): Boolean {
        return place.visited == 1
    }

    fun isImageEmpty(): Boolean {
        return place.imageUri.isEmpty()
    }

    fun isMenuGradeEmpty(): Boolean {
        return place.menuGrade.isEmpty()
    }

    fun insertPlace(place: Place) {
        placeDto.insertPlaceList(place)
    }

    fun updatePlace(place: Place) {
        placeDto.insertPlaceList(place)
    }

    fun deletePlace() {
        placeDto.deletePlaceList(place.id)
    }
}