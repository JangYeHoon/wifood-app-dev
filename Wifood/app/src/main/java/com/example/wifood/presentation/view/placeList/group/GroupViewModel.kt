package com.example.wifood.presentation.view.placeList.group

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.usecase.WifoodUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val useCases: WifoodUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var formState by mutableStateOf(GroupFormState())
    var state by mutableStateOf(GroupState())

    init {
        savedStateHandle.get<Group>("group")?.let { group ->
            formState = formState.copy(name = group.name, description = group.description)
            state = state.copy(group = group)
        }
    }

    fun onEvent(event: GroupFormEvent) {
        when (event) {
            is GroupFormEvent.NameChange -> {
                formState = formState.copy(name = event.name)
            }
            is GroupFormEvent.DescriptionChange -> {
                formState = formState.copy(description = event.description)
            }
            is GroupFormEvent.SaveBtnClick -> {
                TODO()
            }
        }
    }
}