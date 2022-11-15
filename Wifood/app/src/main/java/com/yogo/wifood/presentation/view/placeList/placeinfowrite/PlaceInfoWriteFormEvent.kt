package com.yogo.wifood.presentation.view.placeList.placeinfowrite

import android.location.Location
import android.net.Uri
import com.yogo.wifood.domain.model.Group
import com.yogo.wifood.domain.model.Place
import com.yogo.wifood.domain.model.TMapSearch

sealed class PlaceInfoWriteFormEvent {
    data class GroupSelected(val group: Group) : PlaceInfoWriteFormEvent()
    data class SearchPlaceSelected(val searchPlace: TMapSearch) : PlaceInfoWriteFormEvent()
    data class VisitedCheck(val visited: Boolean) : PlaceInfoWriteFormEvent()
    data class ScoreChange(val selectedStarIdx: Int) : PlaceInfoWriteFormEvent()
    data class TasteCheck(val tasteChk: Boolean) : PlaceInfoWriteFormEvent()
    data class CleanCheck(val cleanChk: Boolean) : PlaceInfoWriteFormEvent()
    data class KindCheck(val kindChk: Boolean) : PlaceInfoWriteFormEvent()
    data class VibeCheck(val vibeChk: Boolean) : PlaceInfoWriteFormEvent()
    data class ReviewChange(val review: String) : PlaceInfoWriteFormEvent()
    data class MenuNameChange(val idx: Int, val menuName: String) : PlaceInfoWriteFormEvent()
    data class MenuPriceChange(val idx: Int, val menuPrice: String) : PlaceInfoWriteFormEvent()
    data class MenuMemoChange(val idx: Int, val menuMemo: String) : PlaceInfoWriteFormEvent()
    data class PlaceImagesAdd(val image: Uri) : PlaceInfoWriteFormEvent()
    data class ImageNameChange(val imageName: String) : PlaceInfoWriteFormEvent()
    data class CurrentLocationChange(val location: Location) : PlaceInfoWriteFormEvent()
    data class BackBtnClick(val place: Place) : PlaceInfoWriteFormEvent()
    object MenuGradeAddBtnClick : PlaceInfoWriteFormEvent()
    object PlaceAddBtnClick : PlaceInfoWriteFormEvent()
    object PlaceEditBtnClick : PlaceInfoWriteFormEvent()
}