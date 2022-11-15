package com.yogo.wifood.presentation.view.placeList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.yogo.wifood.domain.model.Group
import com.yogo.wifood.domain.model.Place
import com.yogo.wifood.domain.usecase.WifoodUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PlaceInfoViewModel @Inject constructor(
    private val useCases: WifoodUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(PlaceInfoState())

    init {
        savedStateHandle.get<Place>("place")?.let { place ->
            state = state.copy(place = place, placeReview = place.review)
        }

        savedStateHandle.get<Group>("group")?.let { group ->
            state = state.copy(group = group)
        }

        Timber.i("get place info from PlaceList : " + state.place.toString() + ", " + state.group)

        state.place?.let {
            useCases.GetPlaceImageUris(it.groupId, it.placeId).observeForever { uris ->
                state = state.copy(placeImageUris = uris)
                Timber.i("get image uri list from firebase : " + state.placeImageUris.toString())
            }
        }
    }

    fun onEvent(event: PlaceInfoEvent) {
        when (event) {
            is PlaceInfoEvent.PlaceDeleteEvent -> {
                useCases.DeletePlaceImages(state.place!!.groupId, state.place!!.placeId)
                useCases.DeletePlace(state.place!!.groupId, state.place!!.placeId)
            }
            is PlaceInfoEvent.ClickPlaceImage -> {
                state =
                    state.copy(popupImageIdx = event.imageIdx)
            }
            is PlaceInfoEvent.ClickPopupLeft -> {
                state = if (state.popupImageIdx == 0)
                    state.copy(popupImageIdx = state.placeImageUris.size - 1)
                else
                    state.copy(popupImageIdx = state.popupImageIdx - 1)
            }
            is PlaceInfoEvent.ClickPopupRight -> {
                state = if (state.popupImageIdx == state.placeImageUris.size - 1)
                    state.copy(popupImageIdx = 0)
                else
                    state.copy(popupImageIdx = state.popupImageIdx + 1)
            }
            is PlaceInfoEvent.ReviewChange -> {
                state = state.copy(placeReview = event.review)
            }
            is PlaceInfoEvent.ViewChangeEvent -> {
                state.place!!.review = state.placeReview
                useCases.UpdatePlace(state.place!!)
            }
        }
    }
}