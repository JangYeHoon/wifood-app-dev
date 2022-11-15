package com.yogo.wifood.presentation.view.placeList

sealed class PlaceInfoEvent {
    data class ClickPlaceImage(val imageIdx: Int) : PlaceInfoEvent()
    data class ReviewChange(val review: String) : PlaceInfoEvent()
    object ClickPopupLeft : PlaceInfoEvent()
    object ClickPopupRight : PlaceInfoEvent()
    object PlaceDeleteEvent : PlaceInfoEvent()
    object ViewChangeEvent : PlaceInfoEvent()
}
