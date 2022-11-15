package com.yogo.wifood.presentation.view.main

import android.location.Location
import com.yogo.wifood.domain.model.Group
import com.yogo.wifood.domain.model.TMapSearch
import com.google.android.gms.maps.model.LatLng

sealed class MainEvent {
    data class ItemClicked(val selected: String) : MainEvent()
    data class GroupClicked(val selectedGroupId: Int) : MainEvent()
    data class GroupSheetClicked(val selectedGroup: Group) : MainEvent()
    data class DeleteGroupEvent(val groupId: Int) : MainEvent()
    data class LocationChanged(val location: Location) : MainEvent()
    data class CameraMove(val latLng: LatLng) : MainEvent()
    data class SearchClicked(val searchPlaceResult: TMapSearch) : MainEvent()
}
