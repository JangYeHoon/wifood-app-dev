package com.yogo.wifood.presentation.viewmodel
//
//import androidx.lifecycle.ViewModel
//import com.yogo.wifood.dto.PlaceDto
//import com.yogo.wifood.domain.model.MenuGrade
//import com.yogo.wifood.domain.model.Place
//
//class PlaceViewModel : ViewModel() {
//    private val placeDto: PlaceDto = PlaceDto()
//    var place: Place = Place()
//
//    fun initPlace(place: Place) {
//        this.place = place
//    }
//
//    fun initImageList() {
//        place.imageName.clear()
//    }
//
//    fun initMenuGrade() {
//        place.menuGrade.clear()
//    }
//
//    fun getPlaceInstance(): Place {
//        return place
//    }
//
//    fun getPlaceName(): String {
//        return place.name
//    }
//
//    fun setPlaceName(name: String) {
//        place.name = name
//    }
//
//    fun getPlaceAddress(): String {
//        return place.address
//    }
//
//    fun setPlaceAddress(address: String) {
//        place.address = address
//    }
//
//    fun getPlaceMemo(): String {
//        return place.memo
//    }
//
//    fun setPlaceMemo(memo: String) {
//        place.memo = memo
//    }
//
//    fun getTasteGrade(): Double {
//        return place.myTasteGrade
//    }
//
//    fun setTasteGrade(taste: Double) {
//        place.myTasteGrade = taste
//    }
//
//    fun getKindnessGrade(): Double {
//        return place.myKindnessGrade
//    }
//
//    fun setKindnessGrade(kindness: Double) {
//        place.myKindnessGrade = kindness
//    }
//
//    fun getCleanGrade(): Double {
//        return place.myCleanGrade
//    }
//
//    fun setCleanGrade(clean: Double) {
//        place.myCleanGrade = clean
//    }
//
//    fun getMenuListToString(): String {
//        var ret = ""
//        for (i in 0 until place.menu.size) {
//            ret += place.menu[i].name
//            if (i != place.menu.size - 1)
//                ret += ","
//        }
//        return ret
//    }
//
//    fun getMenuGradeList(): ArrayList<MenuGrade> {
//        return place.menuGrade
//    }
//
//    fun getPlaceLatitude(): Double {
//        return place.latitude
//    }
//
//    fun setPlaceLatitude(latitude: Double) {
//        place.latitude = latitude
//    }
//
//    fun getPlaceLongitude(): Double {
//        return place.longitude
//    }
//
//    fun setPlaceLongitude(longitude: Double) {
//        place.longitude = longitude
//    }
//
//    fun getImageName(): String {
//        return place.imageName[0]
//    }
//
//    fun setImageName(imageList: ArrayList<String>) {
//        place.imageName = imageList
//    }
//
//    fun getPlaceId(): Int {
//        return place.id
//    }
//
//    fun setPlaceId(placeId: Int) {
//        place.id = placeId
//    }
//
//    fun getPlaceGroupId(): Int {
//        return place.groupId
//    }
//
//    fun setPlaceGroupId(groupId: Int) {
//        place.groupId = groupId
//    }
//
//    fun isVisited(): Boolean {
//        return place.visited == 1
//    }
//
//    fun isPlaceNameEmpty(): Boolean {
//        return place.name == "None" || place.name == ""
//    }
//
//    fun isPlaceGroupEmpty(): Boolean {
//        return place.groupId == -1
//    }
//
//    fun isImageEmpty(): Boolean {
//        return place.imageName.isEmpty()
//    }
//
//    fun isMenuGradeEmpty(): Boolean {
//        return place.menuGrade.isEmpty()
//    }
//
//    fun setVisited(visited: Int) {
//        place.visited = visited
//    }
//
//    fun insertMenuGrade(menuGrade: MenuGrade) {
//        place.menuGrade.add(menuGrade)
//    }
//
//    fun insertImageName(imageName: String) {
//        place.imageName.add(imageName)
//    }
//
//    fun insertPlace(place: Place) {
//        placeDto.insertPlaceList(place)
//    }
//
//    fun updatePlaceGrade(taste: Double, clean: Double, kindness: Double) {
//        place.myTasteGrade = taste
//        place.myCleanGrade = clean
//        place.myKindnessGrade = kindness
//    }
//
//    fun updatePlace(place: Place) {
//        placeDto.insertPlaceList(place)
//    }
//
//    fun deleteMenuByIdx(idx: Int) {
//        place.menu.removeAt(idx)
//    }
//
//    fun deletePlace() {
//        placeDto.deletePlaceList(place.id)
//    }
//}