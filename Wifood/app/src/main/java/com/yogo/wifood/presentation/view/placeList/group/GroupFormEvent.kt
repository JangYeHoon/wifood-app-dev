package com.yogo.wifood.presentation.view.placeList.group

sealed class GroupFormEvent {
    data class NameChange(val name: String) : GroupFormEvent()
    data class DescriptionChange(val description: String) : GroupFormEvent()
    object AddBtnClick : GroupFormEvent()
    object EditBtnClick : GroupFormEvent()
    object ResetNameText : GroupFormEvent()
    object ResetDescriptionText : GroupFormEvent()
}
