package com.example.wifood.presentation.view.search

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.wifood.domain.usecase.WifoodUseCases
import com.skt.Tmap.TMapTapi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SearchPlaceViewModel @Inject constructor(
    private val useCases: WifoodUseCases,
    savedStateHandle: SavedStateHandle,
    @ApplicationContext applicationContext: Context,
) : ViewModel() {
    var formState by mutableStateOf(SearchPlaceFormState())
    val tMapTapi = TMapTapi(applicationContext)

    init {
        tMapTapi.setSKTMapAuthentication("l7xx56bf2cddf5f84556bdf35558d72f530a")
    }

    fun onEvent(event: SearchPlaceFormEvent) {
        when (event) {
            is SearchPlaceFormEvent.SearchKeywordChange -> {
                formState = formState.copy(searchKeyword = event.searchKeyword)
            }
            is SearchPlaceFormEvent.SearchButtonClick -> {
                useCases.GetTMapSearchPlaceResult(
                    formState.searchKeyword,
                    formState.currentLocation!!
                ).observeForever {
                    formState = formState.copy(searchResults = it)
                }
            }
            is SearchPlaceFormEvent.CurrentLocationChange -> {
                formState = formState.copy(currentLocation = event.location)
            }
            is SearchPlaceFormEvent.AddPlaceNameChange -> {
                formState = formState.copy(addPlaceName = event.placeName)
            }
            is SearchPlaceFormEvent.ClickNextBtn -> {
                formState =
                    formState.copy(addPlaceContentPageCount = formState.addPlaceContentPageCount + 1)
            }
            is SearchPlaceFormEvent.AddPlaceAddressChange -> {
                formState = formState.copy(addPlaceAddressSearch = event.searchAddress)
            }
            is SearchPlaceFormEvent.AddressSearchButtonClick -> {
                useCases.GetTMapSearchDetailAddressResult(
                    formState.addPlaceAddressSearch
                ).observeForever {
                    formState = formState.copy(addressSearchResults = it)
                }
            }
        }
    }
}