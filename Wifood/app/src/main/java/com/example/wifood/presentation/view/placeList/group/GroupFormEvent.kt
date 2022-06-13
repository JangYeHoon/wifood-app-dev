package com.example.wifood.presentation.view.placeList.group

sealed class GroupFormEvent {
    data class NameChange(val name: String) : GroupFormEvent()
    data class DescriptionChange(val description: String) : GroupFormEvent()
    object SaveBtnClick : GroupFormEvent()
}
