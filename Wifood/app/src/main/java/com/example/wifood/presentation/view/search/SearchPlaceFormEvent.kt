package com.example.wifood.presentation.view.search

import android.location.Location

sealed class SearchPlaceFormEvent {
    data class SearchKeywordChange(val searchKeyword: String) : SearchPlaceFormEvent()
    data class CurrentLocationChange(val location: Location) : SearchPlaceFormEvent()
    object SearchButtonClick : SearchPlaceFormEvent()
}