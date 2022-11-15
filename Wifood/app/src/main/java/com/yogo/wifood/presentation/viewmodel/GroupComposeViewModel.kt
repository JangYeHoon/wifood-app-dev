package com.yogo.wifood.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.yogo.wifood.domain.model.Group

class GroupComposeViewModel : ViewModel() {
//    private val _group = mutableStateOf(Group())
//    val group: State<Group> = _group

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _theme = mutableStateOf("")
    val theme: State<String> = _theme

    private val _red = mutableStateOf(255F)
    val red: State<Float> = _red

    private val _blue = mutableStateOf(255F)
    val blue: State<Float> = _blue

    private val _green = mutableStateOf(255F)
    val green: State<Float> = _green

//    fun initGroup(group: Group) {
//        _group.value = group
//        _name.value = group.name
////        _theme.value = group.theme
//    }

    fun updateGroupName(name: String) {
        _name.value = name
    }

    fun updateGroupTheme(theme: String) {
        _theme.value = theme
    }

    fun updatePinByColor(color: Color) {
        _red.value = color.red
        _blue.value = color.blue
        _green.value = color.green
    }
}