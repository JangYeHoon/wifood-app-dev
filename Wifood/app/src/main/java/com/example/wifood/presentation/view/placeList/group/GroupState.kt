package com.example.wifood.presentation.view.placeList.group

import com.example.wifood.domain.model.Group

data class GroupState(
    val group: Group? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)
