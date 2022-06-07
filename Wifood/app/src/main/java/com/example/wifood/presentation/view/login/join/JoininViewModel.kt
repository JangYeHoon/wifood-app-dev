package com.example.wifood.presentation.view.login.join

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wifood.domain.usecase.WifoodUseCases
import com.example.wifood.presentation.view.login.util.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoininViewModel @Inject constructor(
    private val useCases: WifoodUseCases
) : ViewModel() {

    var formState by mutableStateOf(JoininFormState())

    private val validateEventChannel = Channel<ValidationEvent>()
    val validationEvents = validateEventChannel.receiveAsFlow()

    fun onEvent(event: JoininEvent) {
        when (event) {
            is JoininEvent.EmailChanged -> {
                formState = formState.copy(email = event.email)
            }
            is JoininEvent.PasswordChanged -> {
                formState = formState.copy(password = event.password)
            }
            is JoininEvent.RepeatedPasswordChanged -> {
                formState = formState.copy(repeatedPassword = event.repeatedPassword)
            }
            is JoininEvent.NicknameChanged -> {
                formState = formState.copy(nickname = event.nickname)
            }
            is JoininEvent.PhoneChanged -> {
                formState = formState.copy(phoneNumber = event.phoneNumber)
            }
            is JoininEvent.ValidNChanged -> {
                formState = formState.copy(validNumber = event.validNumber)
            }
            is JoininEvent.AddressChanged -> {
                formState = formState.copy(address = event.address)
            }
            is JoininEvent.DetailedAChanged -> {
                formState = formState.copy(detailedAddress = event.detailedAddress)
            }
            is JoininEvent.BirthChanged -> {
                formState = formState.copy(birthday = event.birthday)
            }
            is JoininEvent.TermsClicked -> {
                formState = formState.copy(terms = !formState.terms)
            }
            is JoininEvent.Joinin -> {
                formCheck()
            }
        }
    }

    fun formCheck() {
        val emailResult = useCases.validateEmail(formState.email)
        val passwordResult = useCases.validatePassword(formState.password)
        val repeatedPasswordResult =
            useCases.validateRepeatedPassword(formState.password, formState.repeatedPassword)
        //val nicknameResult = useCases.

        val hasError = listOf(
            emailResult,
            passwordResult,
            repeatedPasswordResult
        ).any { !it.successful }

        if (hasError) {
            formState = formState.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            return
        }

        viewModelScope.launch {
            validateEventChannel.send(ValidationEvent.Success)
        }
    }
}