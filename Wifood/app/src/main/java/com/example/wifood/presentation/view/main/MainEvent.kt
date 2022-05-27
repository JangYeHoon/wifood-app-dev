package com.example.wifood.presentation.view.main

sealed class MainEvent {
    data class itemClicked(val selected: String) : MainEvent()
}
