package com.example.wifood.presentation.view.placeList

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.wifood.domain.usecase.WifoodUseCases
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PlaceInfoWriteViewModel @Inject constructor(
    private val useCases: WifoodUseCases,
    @ApplicationContext applicationContext: Context,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val field = listOf(Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS)
    var state by mutableStateOf(PlaceInfoWriteState())
    var formState by mutableStateOf(PlaceInfoWriteFormState())

    init {
        Places.initialize(applicationContext, "AIzaSyB_HZJANQB8zVtH33wHb2OI-FbeDhPYRtA")
        savedStateHandle.get<com.example.wifood.domain.model.Place>("place")?.let { place ->
            state = state.copy(place = place)
        }

        useCases.GetGroups().observeForever {
            formState = formState.copy(groups = it)
            Timber.i("get groups from firebase $it")
        }
    }

    fun onEvent(event: PlaceInfoWriteFormEvent) {
        when (event) {
            is PlaceInfoWriteFormEvent.GroupSelected -> {
                formState = formState.copy(groupName = event.group.name)
                state = state.copy(groupId = event.group.groupId)
            }
            is PlaceInfoWriteFormEvent.PlaceSelected -> {
                updatePlaceFromSearchGoogleAPI(event.searchPlace)
            }
        }
    }

    private fun updatePlaceFromSearchGoogleAPI(searchPlace: Place) {
        formState = formState.copy(placeName = searchPlace.name)
    }
}