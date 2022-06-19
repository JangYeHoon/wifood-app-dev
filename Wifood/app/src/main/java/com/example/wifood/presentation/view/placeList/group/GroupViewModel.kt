package com.example.wifood.presentation.view.placeList.group

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.usecase.WifoodUseCases
import com.example.wifood.presentation.util.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val useCases: WifoodUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var formState by mutableStateOf(GroupFormState())
    var state by mutableStateOf(GroupState())
    private val validateEventChannel = Channel<ValidationEvent>()
    val validationEvents = validateEventChannel.receiveAsFlow()

    init {
        savedStateHandle.get<Group>("group")?.let { group ->
            if (group.name.isNotEmpty()) {
                formState = formState.copy(name = group.name, description = group.description)
                state = state.copy(group = group)
            }
        }
    }

    suspend fun onEvent(event: GroupFormEvent) {
        when (event) {
            is GroupFormEvent.NameChange -> {
                formState = formState.copy(name = event.name)
            }
            is GroupFormEvent.DescriptionChange -> {
                formState = formState.copy(description = event.description)
            }
            is GroupFormEvent.SaveBtnClick -> {
                if (formCheck())
                    insertGroup()
            }
        }
    }

    private fun formCheck(): Boolean {
        val nameValidateResult = useCases.validateGroupName(formState.name)
        if (!nameValidateResult.successful) {
            formState = formState.copy(nameError = nameValidateResult.errorMessage)
            return false
        }
        return true
    }

    private suspend fun insertGroup() {
        if (state.group != null) {
            state.group!!.name = formState.name
            state.group!!.description = formState.description
            useCases.UpdateGroup(state.group!!)
        } else {
            // TODO "group id 설정 및 pin color random 설정
            val group =
                Group(88, "kmh@naver.com", formState.name, formState.description, 1, emptyList())
            useCases.InsertGroup(group)
        }
        Timber.i("group insert to firebase")
        validateEventChannel.send(ValidationEvent.Success)
    }
}