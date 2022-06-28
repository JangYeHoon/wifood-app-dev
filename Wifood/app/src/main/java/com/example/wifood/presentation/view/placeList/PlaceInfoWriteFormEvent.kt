package com.example.wifood.presentation.view.placeList

import com.example.wifood.domain.model.Group
import com.google.android.libraries.places.api.model.Place

sealed class PlaceInfoWriteFormEvent {
    data class GroupSelected(val group: Group) : PlaceInfoWriteFormEvent()
    data class PlaceSelected(val searchPlace: Place) : PlaceInfoWriteFormEvent()
    data class MenuChange(val menu: String) : PlaceInfoWriteFormEvent()
}