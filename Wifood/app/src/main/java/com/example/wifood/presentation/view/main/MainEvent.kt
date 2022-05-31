package com.example.wifood.presentation.view.main

sealed class MainEvent {
    data class ItemClicked(val selected: String) : MainEvent()
    data class GroupClicked(val selectedGroupId: Int) : MainEvent()
}
