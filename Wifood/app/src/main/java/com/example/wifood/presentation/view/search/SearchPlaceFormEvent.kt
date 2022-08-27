package com.example.wifood.presentation.view.search

import android.location.Location

sealed class SearchPlaceFormEvent {
    data class SearchKeywordChange(val searchKeyword: String) : SearchPlaceFormEvent()
    data class CurrentLocationChange(val location: Location) : SearchPlaceFormEvent()
    data class AddPlaceNameChange(val placeName: String) : SearchPlaceFormEvent()
    data class AddPlaceAddressChange(val searchAddress: String) : SearchPlaceFormEvent()
    object ClickNextBtn : SearchPlaceFormEvent()
    object SearchButtonClick : SearchPlaceFormEvent()
    object AddressSearchButtonClick : SearchPlaceFormEvent()
}