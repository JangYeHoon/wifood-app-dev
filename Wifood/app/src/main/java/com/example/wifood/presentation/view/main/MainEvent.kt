package com.example.wifood.presentation.view.main

import android.location.Location
import com.google.android.gms.maps.model.LatLng

sealed class MainEvent {
    data class ItemClicked(val selected: String) : MainEvent()
    data class GroupClicked(val selectedGroupId: Int) : MainEvent()
    data class DeleteGroupEvent(val groupId: Int) : MainEvent()
    data class LocationChanged(val location: Location) : MainEvent()
    data class CameraMove(val latLng: LatLng) : MainEvent()
}
