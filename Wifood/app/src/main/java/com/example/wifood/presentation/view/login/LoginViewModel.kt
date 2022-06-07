package com.example.wifood.presentation.view.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wifood.domain.model.Place
import com.example.wifood.domain.usecase.WifoodUseCases
import com.example.wifood.presentation.view.login.util.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCases: WifoodUseCases
) : ViewModel() {

    var formState by mutableStateOf(LoginFormState())

    var state by mutableStateOf(LoginState())

    private val validateEventChannel = Channel<ValidationEvent>()
    val validationEvents = validateEventChannel.receiveAsFlow()

    suspend fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChanged -> {
                formState = formState.copy(email = event.email)
            }
            is LoginFormEvent.PasswordChanged -> {
                formState = formState.copy(password = event.password)
            }
            is LoginFormEvent.Login -> {
                val result = viewModelScope.async {
                    formCheck()
                }
                try {
                    if (result.await()) {
                        loginData()
                    }
                } catch (e: Exception) {
                    validateEventChannel.send(ValidationEvent.Error("로그인 정보를 불러오는 중입니다...(다시 로그인)"))
                }
            }
        }
    }

    private fun formCheck(): Boolean {
        val emailResult = useCases.validateEmail(formState.email)
        val passwordResult = useCases.validatePassword(formState.password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.successful }

        if (hasError) {
            formState = formState.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            return false
        }

        useCases.GetUser(formState.email.replace('.', '_')).observeForever {
            state = state.copy(
                user = it,
                groups = it.groupList,
                isLoading = true
            )
            val placeList = mutableListOf<Place>()
            state.groups.forEach { group ->
                group.placeList.forEach { place ->
                    placeList.add(place)
                }
            }
            state = state.copy(
                places = placeList,
                isLoading = false
            )
        }
        return true
    }

    private suspend fun loginData() {
        val valid = state.user!!.pwd == formState.password
        if (valid) {
            validateEventChannel.send(ValidationEvent.Success)
        } else {
            validateEventChannel.send(ValidationEvent.Error("비밀번호가 일치하지 않습니다."))
        }
    }
}