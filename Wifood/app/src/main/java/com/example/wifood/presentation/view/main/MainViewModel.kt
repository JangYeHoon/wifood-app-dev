package com.example.wifood.presentation.view.main

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wifood.presentation.view.login.LoginFormEvent
import com.example.wifood.presentation.view.login.LoginFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {
    private val _toast = MutableSharedFlow<String>()
    val toast = _toast.asSharedFlow()

    var state by mutableStateOf(MainState())

    init {

    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.itemClicked -> {
                state = state.copy(selected = event.selected)
            }
        }
    }

    fun showSnackBar(message: String) {
        viewModelScope.launch {
            _toast.emit(message)
        }
    }
}