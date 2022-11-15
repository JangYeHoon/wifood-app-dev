package com.yogo.wifood.presentation.view.placeList
//
//import androidx.compose.runtime.State
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.ViewModel
//import com.yogo.wifood.domain.model.Place
//import java.lang.Integer.max
//
//class PlaceListViewModel() : ViewModel() {
////    var placeList: LiveData<MutableList<Place>>
////    private val placeDto: PlaceDto = PlaceDto()
//
//    private val _visible = mutableStateOf(false)
//    val visible: State<Boolean> = _visible
//
//    init {
////        placeList = placeDto.getPlaceList()
//    }
//
//    fun expandVisibility() {
//        _visible.value = true
//    }
//
//    fun collapseVisibility() {
//        _visible.value = false
//    }
//
//    fun getPlaceListMaxId(): Int {
//        val place = placeList.value
//        var maxValue = 0
////        if (place != null)
////            for (f in place)
////                maxValue = max(f.id, maxValue)
//        return maxValue
//    }
//
//    fun getPlaceListByGroupId(groupId: Int): MutableList<Place>? {
//        val place = mutableListOf<Place>()
//        if (groupId != -1 && placeList.value != null) {
//            for (f in placeList.value!!) {
//                if (groupId == f.groupId)
//                    place.add(f)
//            }
//            return place
//        }
//        return placeList.value
//    }
//
////    fun insertPlace(place: Place) {
////        placeDto.insertPlaceList(place)
////    }
////
////    fun updatePlace(place: Place) {
////        placeDto.insertPlaceList(place)
////    }
////
////    fun deletePlace(placeId: Int) {
////        placeDto.deletePlaceList(placeId)
////    }
//}