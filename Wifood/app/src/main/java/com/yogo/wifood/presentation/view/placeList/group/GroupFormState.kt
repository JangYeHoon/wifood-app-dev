package com.yogo.wifood.presentation.view.placeList.group

import com.yogo.wifood.domain.model.Group

data class GroupFormState(
    val name: String = "",
    val description: String = "",
    val nameError: String? = null
)
