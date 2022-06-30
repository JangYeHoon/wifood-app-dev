package com.example.wifood.presentation.view.placeList

import com.example.wifood.domain.model.Group
import com.google.android.libraries.places.api.model.Place

sealed class PlaceInfoWriteFormEvent {
    data class GroupSelected(val group: Group) : PlaceInfoWriteFormEvent()
    data class PlaceSelected(val searchPlace: Place) : PlaceInfoWriteFormEvent()
    data class MenuChange(val menu: String) : PlaceInfoWriteFormEvent()
    data class VisitedCheck(val visited: Boolean) : PlaceInfoWriteFormEvent()
    data class ScoreChange(val score: Float) : PlaceInfoWriteFormEvent()
    data class TasteCheck(val tasteChk: Boolean) : PlaceInfoWriteFormEvent()
    data class CleanCheck(val cleanChk: Boolean) : PlaceInfoWriteFormEvent()
    data class KindCheck(val kindChk: Boolean) : PlaceInfoWriteFormEvent()
    data class VibeCheck(val vibeChk: Boolean) : PlaceInfoWriteFormEvent()
    data class ReviewChange(val review: String) : PlaceInfoWriteFormEvent()
    data class MenuNameChange(val menuName: String)  : PlaceInfoWriteFormEvent()
    data class MenuPriceChange(val menuPrice: String) : PlaceInfoWriteFormEvent()
    data class MenuMemoChange(val menuMemo: String) : PlaceInfoWriteFormEvent()
}