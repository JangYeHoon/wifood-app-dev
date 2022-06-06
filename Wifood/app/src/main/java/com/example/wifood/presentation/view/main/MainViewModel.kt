package com.example.wifood.presentation.view.main

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wifood.domain.model.Place
import com.example.wifood.domain.usecase.WifoodUseCases
import com.example.wifood.presentation.view.login.LoginFormEvent
import com.example.wifood.presentation.view.login.LoginFormState
import com.example.wifood.util.Resource
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCases: WifoodUseCases
) : ViewModel() {
    private val _toast = MutableSharedFlow<String>()
    val toast = _toast.asSharedFlow()

    var state by mutableStateOf(MainState())

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.ItemClicked -> {
                state = state.copy(selected = event.selected)
            }
            is MainEvent.GroupClicked -> {
                state = state.copy(selectedGroupId = event.selectedGroupId)
            }
            is MainEvent.DeleteGroupEvent -> {
                useCases.DeleteGroup(event.groupId)
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
        useCases.GetUser("kmh@naver.com").observeForever {
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
            Log.d("MainViewModel", "get user from firebase : ${state.user.toString()}")
        }
    }
}

sealed class UiEvent {
    data class ShowSnackBar(val message: String) : UiEvent()
}