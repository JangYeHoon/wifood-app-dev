package com.yogo.wifood.presentation.view.placeList.group

import com.yogo.wifood.domain.model.Group

data class GroupState(
    val group: Group? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)
