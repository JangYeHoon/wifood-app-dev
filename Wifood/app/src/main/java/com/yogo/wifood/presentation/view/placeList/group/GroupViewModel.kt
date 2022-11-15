package com.yogo.wifood.presentation.view.placeList.group

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.yogo.wifood.WifoodApp
import com.yogo.wifood.domain.model.Group
import com.yogo.wifood.domain.usecase.WifoodUseCases
import com.yogo.wifood.presentation.util.ValidationEvent
import com.yogo.wifood.presentation.view.main.util.MainData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber
import java.util.concurrent.ThreadLocalRandom
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
            formState = formState.copy(name = group.name, description = group.description)
            state = state.copy(group = group)
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
            is GroupFormEvent.ResetNameText -> {
                formState = formState.copy(name = "")
            }
            is GroupFormEvent.ResetDescriptionText -> {
                formState = formState.copy(description = "")
            }
            is GroupFormEvent.AddBtnClick -> {
                insertGroup()
            }
            is GroupFormEvent.EditBtnClick -> {
                editGroup()
            }
        }
    }

    private suspend fun insertGroup() {
        useCases.InsertGroup(createGroupEntity())
        Timber.i("group insert to firebase")
        validateEventChannel.send(ValidationEvent.Success)
    }

    private fun createGroupEntity(): Group {
        val groupId = com.yogo.wifood.WifoodApp.pref.getInt("group_max_id", -1)
        val userId = com.yogo.wifood.WifoodApp.pref.getString("user_id", "No user data")
        return Group(
            groupId,
            MainData.user.phoneNumber,
            formState.name,
            formState.description,
            ThreadLocalRandom.current().nextInt(1, 11),
            emptyList()
        )
    }

    private suspend fun editGroup() {
        state.group!!.name = formState.name
        state.group!!.description = formState.description
        useCases.UpdateGroup(state.group!!)
        Timber.i("group update to firebase")
        validateEventChannel.send(ValidationEvent.Success)
    }
}