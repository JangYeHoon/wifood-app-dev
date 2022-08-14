package com.example.wifood.presentation.view.login.join

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wifood.domain.model.User
import com.example.wifood.domain.usecase.WifoodUseCases
import com.example.wifood.presentation.view.login.util.ValidationEvent
import com.example.wifood.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class JoininViewModel @Inject constructor(
    private val useCases: WifoodUseCases
) : ViewModel() {

    var formState by mutableStateOf(JoininFormState())

    private val validateEventChannel = Channel<ValidationEvent>()
    val validationEvents = validateEventChannel.receiveAsFlow()

    suspend fun onEvent(event: JoininEvent) {
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

    private suspend fun formCheck() {
        val emailResult = useCases.validateEmail(formState.email)
        val passwordResult = useCases.validatePassword(formState.password)
        val repeatedPasswordResult =
            useCases.validateRepeatedPassword(formState.password, formState.repeatedPassword)
        val nicknameResult = useCases.validateNickname(formState.nickname)
        //val birthdayResult = useCases.validateBirthday(formState.birthday)
        val termsResult = useCases.validateTerms(formState.terms)

        val hasError = listOf(
            emailResult,
            passwordResult,
            repeatedPasswordResult,
            nicknameResult,
            //birthdayResult,
            termsResult
        ).any { !it.successful }

        if (hasError) {
            formState = formState.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatedPasswordResult.errorMessage,
                nicknameError = nicknameResult.errorMessage,
                //birthdayError = birthdayResult.errorMessage,
                termsError = termsResult.errorMessage
            )
            return
        }

        val user = User(
            userId = formState.email,
            pwd = formState.repeatedPassword,
            nickname = formState.nickname,
            phoneNumber = formState.phoneNumber,
            address = formState.address.plus(formState.detailedAddress),
            birthday = formState.birthday,
            gender = 1
        )

        val resource = viewModelScope.async {
            Log.d("TEST", "Usecase called")
            useCases.InsertUser(user)
        }

        resource.await().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    formState = formState.copy(isLoading = false)
                    validateEventChannel.send(ValidationEvent.Success)
                }
                is Resource.Error -> {
                    validateEventChannel.send(ValidationEvent.Error(result.message ?: "알 수 없는 에러"))
                }
                is Resource.Loading -> {
                    formState = formState.copy(isLoading = true)
                }
            }
        }


    }
}