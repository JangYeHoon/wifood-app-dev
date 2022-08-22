package com.example.wifood.presentation.view.placeList

sealed class PlaceInfoEvent {
    data class ClickPlaceImage(val imageIdx: Int) : PlaceInfoEvent()
    object ClickPopupLeft : PlaceInfoEvent()
    object ClickPopupRight : PlaceInfoEvent()
    object PlaceDeleteEvent : PlaceInfoEvent()
}
