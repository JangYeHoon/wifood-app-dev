package com.example.wifood.presentation.view.search

import android.location.Location
import com.example.wifood.domain.model.TMapSearch
import com.google.android.gms.maps.model.LatLng

sealed class SearchPlaceFormEvent {
    data class SearchKeywordChange(val searchKeyword: String) : SearchPlaceFormEvent()
    data class CurrentLocationChange(val location: Location) : SearchPlaceFormEvent()
    data class AddPlaceNameChange(val placeName: String) : SearchPlaceFormEvent()
    data class AddPlaceAddressChange(val searchAddress: String) : SearchPlaceFormEvent()
    data class AddressClick(val address: TMapSearch) : SearchPlaceFormEvent()
    data class GoogleMapLatLngBtnClick(val latLng: LatLng) : SearchPlaceFormEvent()
    data class CameraMove(val latLng: LatLng) : SearchPlaceFormEvent()
    object ClickNextBtn : SearchPlaceFormEvent()
    object SearchButtonClick : SearchPlaceFormEvent()
    object AddressSearchButtonClick : SearchPlaceFormEvent()
}