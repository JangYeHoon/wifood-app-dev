package com.yogo.wifood.presentation.view.search

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.yogo.wifood.domain.model.Place
import com.yogo.wifood.domain.model.TMapSearch
import com.yogo.wifood.domain.usecase.WifoodUseCases
import com.yogo.wifood.presentation.util.ValidationEvent
import com.google.android.gms.maps.model.LatLng
import com.skt.Tmap.TMapTapi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SearchPlaceViewModel @Inject constructor(
    private val useCases: WifoodUseCases,
    savedStateHandle: SavedStateHandle,
    @ApplicationContext applicationContext: Context,
) : ViewModel() {
    var formState by mutableStateOf(SearchPlaceFormState())
    val tMapTapi = TMapTapi(applicationContext)
    private val validateEventChannel = Channel<ValidationEvent>()
    val validationEvents = validateEventChannel.receiveAsFlow()

    init {
        tMapTapi.setSKTMapAuthentication("l7xx56bf2cddf5f84556bdf35558d72f530a")
        savedStateHandle.get<Place>("place")?.let { place ->
            formState = formState.copy(place = place)
        }
    }

    @DelicateCoroutinesApi
    suspend fun onEvent(event: SearchPlaceFormEvent) {
        when (event) {
            is SearchPlaceFormEvent.SearchKeywordChange -> {
                formState = formState.copy(searchKeyword = event.searchKeyword)
            }
            is SearchPlaceFormEvent.SearchButtonClick -> {
                useCases.GetTMapSearchPlaceResult(
                    formState.searchKeyword,
                    formState.currentLocation!!
                ).observeForever {
                    try {
                        formState = formState.copy(searchResults = it as ArrayList<TMapSearch>)
                    } catch (e: ConcurrentModificationException) {
                    }
                }
            }
            is SearchPlaceFormEvent.CurrentLocationChange -> {
                formState = formState.copy(currentLocation = event.location)
            }
            is SearchPlaceFormEvent.AddPlaceNameChange -> {
                formState = formState.copy(addPlaceName = event.placeName)
                formState.place!!.name = formState.addPlaceName
            }
            is SearchPlaceFormEvent.ClickNextBtn -> {
                formState =
                    formState.copy(addPlaceContentPageCount = formState.addPlaceContentPageCount + 1)
            }
            is SearchPlaceFormEvent.BackBtnClick -> {
                formState =
                    formState.copy(addPlaceContentPageCount = formState.addPlaceContentPageCount - 1)
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
            is SearchPlaceFormEvent.AddressClick -> {
                setPlaceFromSearchAddressAndLatLng(
                    LatLng(
                        event.address.latitude,
                        event.address.longitude
                    ), event.address.fullAddress
                )
                formState = formState.copy(clickedAddressIdx = event.addressIndex)
            }
            is SearchPlaceFormEvent.GoogleMapLatLngBtnClick -> {
                useCases.GetTMapReverseGeocoding(event.latLng).observeForever {
                    if (it != null) {
                        GlobalScope.launch(Dispatchers.IO) {
                            withContext(Dispatchers.Main) {
                                setPlaceFromSearchAddressAndLatLng(event.latLng, it)
                                validateEventChannel.send(ValidationEvent.Success)
                            }
                        }
                    }
                }
            }
            is SearchPlaceFormEvent.CameraMove -> {
                useCases.GetTMapReverseGeocoding(event.latLng).observeForever {
                    if (it != null) {
                        val addressSplit = it.split('/')
                        formState = formState.copy(
                            roadAddressGeocoding = addressSplit[0],
                            oldAddressGeocoding = addressSplit[1]
                        )
                    }
                }
            }
            is SearchPlaceFormEvent.InputAddressViewBtnClick -> {
                validateEventChannel.send(ValidationEvent.Success)
            }
            is SearchPlaceFormEvent.InputAddressClear -> {
                formState = formState.copy(addPlaceAddressSearch = "")
            }
            is SearchPlaceFormEvent.InputNameClear -> {
                formState = formState.copy(addPlaceName = "")
            }
        }
    }

    private fun setPlaceFromSearchAddressAndLatLng(latLng: LatLng, address: String) {
        formState.place!!.address = address
        formState.place!!.latitude = latLng.latitude
        formState.place!!.longitude = latLng.longitude
    }
}