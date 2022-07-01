package com.example.wifood.presentation.view.main

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wifood.WifoodApp
import com.example.wifood.domain.model.Place
import com.example.wifood.domain.usecase.WifoodUseCases
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCases: WifoodUseCases
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
        }
    }

    fun onUiEvent(event: UiEvent) {
        when (event) {
            is UiEvent.ShowSnackBar -> {
                viewModelScope.launch { _toast.emit(event.message) }
            }
        }
    }

    fun init() {
        val userId = WifoodApp.pref.getString("user_id", "No user data")
        useCases.GetUser(userId).observeForever { it ->
            state = state.copy(
                user = it,
                groups = it.groupList
            )
            val placeList = mutableListOf<Place>()
            state.groups.forEach { group ->
                group.placeList.forEach { place ->
                    placeList.add(place)
                }
            }
            state = state.copy(places = placeList)
            Timber.i("get user from firebase : " + state.user.toString())

            val groupMaxId =
                state.groups.maxWithOrNull(compareBy { group -> group.groupId })!!.groupId
            val placeMaxId =
                state.places.maxWithOrNull(compareBy { place -> place.placeId })!!.placeId
            WifoodApp.pref.setInt("group_max_id", groupMaxId)
            WifoodApp.pref.setInt("place_max_id", placeMaxId)
        }
    }
}

sealed class UiEvent {
    data class ShowSnackBar(val message: String) : UiEvent()
}