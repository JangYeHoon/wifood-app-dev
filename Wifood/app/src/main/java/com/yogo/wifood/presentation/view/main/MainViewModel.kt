package com.yogo.wifood.presentation.view.main

import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogo.wifood.WifoodApp
import com.yogo.wifood.domain.model.Place
import com.yogo.wifood.domain.model.TMapSearch
import com.yogo.wifood.domain.usecase.WifoodUseCases
import com.yogo.wifood.presentation.view.main.util.MainData
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCases: WifoodUseCases,
    @ApplicationContext applicationContext: Context,
) : ViewModel() {
    private val _toast = MutableSharedFlow<String>()
    val toast = _toast.asSharedFlow()

    var state by mutableStateOf(MainState())

    private val _camera = MutableSharedFlow<LatLng>()
    val camera = _camera.asSharedFlow()

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.ItemClicked -> {
                state = state.copy(selected = event.selected)
            }
            is MainEvent.GroupClicked -> {
                state = state.copy(selectedGroupId = event.selectedGroupId)
            }
            is MainEvent.GroupSheetClicked -> {
                state = state.copy(selectedGroupSheet = event.selectedGroup)
            }
            is MainEvent.DeleteGroupEvent -> {
                useCases.DeleteGroupImages(event.groupId)
                useCases.DeleteGroup(event.groupId)
            }
            is MainEvent.LocationChanged -> {
                state = state.copy(currentLocation = event.location)
            }
            is MainEvent.CameraMove -> {
                viewModelScope.launch {
                    _camera.emit(event.latLng)
                }
            }
            is MainEvent.SearchClicked -> {
                updateSearchResultFromGoogleAPI(event.searchPlaceResult)
            }
        }
    }

    private fun updateSearchResultFromGoogleAPI(searchPlace: TMapSearch) {
        state = state.copy(
            searchResultName = searchPlace.name,
            searchResultLatLng = LatLng(searchPlace.latitude, searchPlace.longitude)
        )
    }

    fun onUiEvent(event: UiEvent) {
        when (event) {
            is UiEvent.ShowSnackBar -> {
                viewModelScope.launch { _toast.emit(event.message) }
            }
        }
    }

    fun init() {
        val userId = com.yogo.wifood.WifoodApp.pref.getString("user_id", "No user data")
        useCases.GetUserAllData(userId).observeForever { it ->
            it?.let {
                state = state.copy(user = it)
                MainData.user = it
                Timber.i("MainData user ${MainData.user.phoneNumber}")
                if (!it.groups.isNullOrEmpty()) {
                    state = state.copy(groups = it.groups)
                    MainData.user.groups = it.groups
                    val placeList = mutableListOf<Place>()
                    state.groups.forEach { group ->
                        group.places.forEach { place ->
                            placeList.add(place)
                        }
                    }
                    state = state.copy(places = placeList)
                } else {
                    state = state.copy(groups = emptyList(), places = emptyList())
                }
                Timber.i("get user from firebase : " + state.user.toString())

                val groupMaxId =
                    state.groups.maxWithOrNull(compareBy { group -> group.groupId })?.groupId
                val placeMaxId =
                    state.places.maxWithOrNull(compareBy { place -> place.placeId })?.placeId
                com.yogo.wifood.WifoodApp.pref.setInt("group_max_id", if (groupMaxId != null) groupMaxId + 1 else 1)
                com.yogo.wifood.WifoodApp.pref.setInt("place_max_id", if (placeMaxId != null) placeMaxId + 1 else 1)

                state.places.forEach { place ->
                    useCases.GetPlaceImageUri(place.groupId, place.placeId).observeForever { uri ->
                        state.placeImages[place.placeId] = uri
                        Timber.i("get image uri list from firebase : $uri")
                    }
                }
                useCases.GetProfile(it.phoneNumber).observeForever { uri ->
                    MainData.image = uri.toString()
                }
            }
        }

    }
}

sealed class UiEvent {
    data class ShowSnackBar(val message: String) : UiEvent()
}