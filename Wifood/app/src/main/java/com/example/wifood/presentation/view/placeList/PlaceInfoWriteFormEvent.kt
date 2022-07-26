package com.example.wifood.presentation.view.placeList

import android.graphics.Bitmap
import android.location.Location
import android.net.Uri
import com.example.wifood.domain.model.Group
import com.google.android.libraries.places.api.model.Place

sealed class PlaceInfoWriteFormEvent {
    data class GroupSelected(val group: Group) : PlaceInfoWriteFormEvent()
    data class PlaceSelected(val searchPlace: Place) : PlaceInfoWriteFormEvent()
    data class VisitedCheck(val visited: Boolean) : PlaceInfoWriteFormEvent()
    data class ScoreChange(val selectedStarIdx: Int) : PlaceInfoWriteFormEvent()
    data class TasteCheck(val tasteChk: Boolean) : PlaceInfoWriteFormEvent()
    data class CleanCheck(val cleanChk: Boolean) : PlaceInfoWriteFormEvent()
    data class KindCheck(val kindChk: Boolean) : PlaceInfoWriteFormEvent()
    data class VibeCheck(val vibeChk: Boolean) : PlaceInfoWriteFormEvent()
    data class ReviewChange(val review: String) : PlaceInfoWriteFormEvent()
    data class MenuNameChange(val menuName: String) : PlaceInfoWriteFormEvent()
    data class MenuPriceChange(val menuPrice: String) : PlaceInfoWriteFormEvent()
    data class MenuMemoChange(val menuMemo: String) : PlaceInfoWriteFormEvent()
    data class PlaceImagesAdd(val image: Uri) : PlaceInfoWriteFormEvent()
    data class ImageNameChange(val imageName: String) : PlaceInfoWriteFormEvent()
    data class CurrentLocationChange(val location: Location) : PlaceInfoWriteFormEvent()
    object MenuGradeAddBtnClick : PlaceInfoWriteFormEvent()
    object PlaceAddBtnClick : PlaceInfoWriteFormEvent()
}