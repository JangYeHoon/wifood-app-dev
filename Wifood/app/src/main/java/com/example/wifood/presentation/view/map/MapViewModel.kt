package com.example.wifood.presentation.view.map

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wifood.data.remote.WifoodApi
import com.example.wifood.domain.usecase.WifoodUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val useCases: WifoodUseCases
) : ViewModel() {
    private val _input = mutableStateOf("")
    val input: State<String> = _input

    private val _toast = MutableSharedFlow<String>()
    val toast = _toast.asSharedFlow()

    private val _state = mutableStateOf(MapState())
    val state: State<MapState> = _state

    private val _readOnly = mutableStateOf(false)
    val readOnly: State<Boolean> = _readOnly

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

    fun getAll(userId: String) {
        Log.d("TEST", "MapViewModel launched")
        useCases.GetAll("kmh@naver.com").onEach {

        }.launchIn(viewModelScope)
    }
}