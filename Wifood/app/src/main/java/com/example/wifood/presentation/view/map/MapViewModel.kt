package com.example.wifood.presentation.view.map

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wifood.data.remote.WifoodApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val api: WifoodApi
): ViewModel() {
    private val _input = mutableStateOf("")
    val input: State<String> = _input

    private val _toast = MutableSharedFlow<String>()
    val toast = _toast.asSharedFlow()

    private val _state = mutableStateOf(MapState())
    val state: State<MapState> = _state

    private val _readOnly = mutableStateOf(false)
    val readOnly: State<Boolean> = _readOnly

    init {
        getGroupList()
        getPlaceList()
    }

    fun inputChanged(text: String) {
        _input.value = text
    }

    fun showSnackBar(message: String) {
        viewModelScope.launch {
            _toast.emit(message)
        }
    }

    fun selectedGroupId(id: Int) {
        _state.value = state.value.copy(
            selected = id
        )
    }

    fun getGroupList() {
        api.getGroupList().observeForever {
            _state.value = state.value.copy(
                groupList = it
            )
        }
    }

    fun getPlaceList() {
        api.getPlaceList().observeForever {
            _state.value = state.value.copy(
                placeList = it
            )
        }
    }
}