package com.example.wifood.presentation.view.main

import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wifood.WifoodApp
import com.example.wifood.domain.model.Place
import com.example.wifood.domain.model.TMapSearch
import com.example.wifood.domain.usecase.WifoodUseCases
import com.example.wifood.presentation.view.main.util.MainData
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
        WifoodApp.pref.setString("user_id", "01033201546")
        val userId = WifoodApp.pref.getString("user_id", "No user data")
        useCases.GetUserAllData(userId).observeForever { it ->
            state = state.copy(user = it)
            MainData.user = it
            Timber.i("MainData user ${MainData.user.phoneNumber}")
            if (!it.groupList.isNullOrEmpty()) {
                state = state.copy(groups = it.groupList)
                MainData.groups = it.groupList
                val placeList = mutableListOf<Place>()
                state.groups.forEach { group ->
                    group.placeList.forEach { place ->
                        placeList.add(place)
                    }
                }
                state = state.copy(places = placeList)
                MainData.places = placeList
            }
            Timber.i("get user from firebase : " + state.user.toString())

            /* 여기 임시로 수정했음 */
            val groupMaxId =
                state.groups.maxWithOrNull(compareBy { group -> group.groupId })?.groupId
            val placeMaxId =
                state.places.maxWithOrNull(compareBy { place -> place.placeId })?.placeId
            WifoodApp.pref.setInt("group_max_id", groupMaxId ?: 1)
            WifoodApp.pref.setInt("place_max_id", placeMaxId ?: 1)
            /* 여기 수정했음 */

            state.places.forEach { place ->
                useCases.GetPlaceImageUri(place.groupId, place.placeId).observeForever { uri ->
                    state.placeImages[place.placeId] = uri
                    Timber.i("get image uri list from firebase : $uri")
                }
            }
        }
    }
}

sealed class UiEvent {
    data class ShowSnackBar(val message: String) : UiEvent()
}