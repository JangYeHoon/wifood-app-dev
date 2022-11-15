package com.yogo.wifood.presentation.view.search

import android.location.Location
import com.yogo.wifood.domain.model.TMapSearch
import com.google.android.gms.maps.model.LatLng

sealed class SearchPlaceFormEvent {
    data class SearchKeywordChange(val searchKeyword: String) : SearchPlaceFormEvent()
    data class CurrentLocationChange(val location: Location) : SearchPlaceFormEvent()
    data class AddPlaceNameChange(val placeName: String) : SearchPlaceFormEvent()
    data class AddPlaceAddressChange(val searchAddress: String) : SearchPlaceFormEvent()
    data class AddressClick(val address: TMapSearch, val addressIndex: Int) : SearchPlaceFormEvent()
    data class GoogleMapLatLngBtnClick(val latLng: LatLng) : SearchPlaceFormEvent()
    data class CameraMove(val latLng: LatLng) : SearchPlaceFormEvent()
    object InputAddressViewBtnClick : SearchPlaceFormEvent()
    object InputAddressClear : SearchPlaceFormEvent()
    object InputNameClear : SearchPlaceFormEvent()
    object ClickNextBtn : SearchPlaceFormEvent()
    object BackBtnClick : SearchPlaceFormEvent()
    object SearchButtonClick : SearchPlaceFormEvent()
    object AddressSearchButtonClick : SearchPlaceFormEvent()
}