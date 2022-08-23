package com.example.wifood.presentation.view.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wifood.WifoodApp
import com.example.wifood.domain.usecase.WifoodUseCases
import com.example.wifood.presentation.view.login.util.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCases: WifoodUseCases
) : ViewModel() {

    var formState by mutableStateOf(LoginFormState())

    var state by mutableStateOf(LoginState())

    private val validateEventChannel = Channel<ValidationEvent>()
    val validationEvents = validateEventChannel.receiveAsFlow()

    // ViewModel 처음 생성될 때 실행되는 코드
    init {
        // 앱이 한 번 이상 실행되었다는 표시
        WifoodApp.pref.setString("Initial_Flag", "1")
    }

    @DelicateCoroutinesApi
    suspend fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                formState = formState.copy(email = event.email)
            }
            is LoginEvent.PasswordChanged -> {
                formState = formState.copy(password = event.password)
            }
            is LoginEvent.Login -> {
                clearError()

                if (formCheck()) {
                    CoroutineScope(Dispatchers.Main).launch {
                        getUserAndLogin()
                    }
                }
            }

            is LoginEvent.CheckUserByEmail -> {
                // 수정 필요함. 잘못된 코드라고 생각하셈
                useCases.GetUserInfo(event.email.replace('.', '_')).observeForever {
                    state = state.copy(user = it)

                    try {
                        GlobalScope.launch(Dispatchers.IO) {
                            withContext(Dispatchers.Main) {
                                if (state.user != null) {
                                    validateEventChannel.send(ValidationEvent.Success)
                                } else {
                                    state = state.copy(email = event.email)
                                    validateEventChannel.send(ValidationEvent.SignUp)
                                }
                            }
                        }
                    } catch (e: Exception) {
                    }
                }
            }
        }
    }

    @DelicateCoroutinesApi
    private suspend fun getUserAndLogin() {
        val handler = CoroutineExceptionHandler { _, throwable ->
            viewModelScope.launch {
                validateEventChannel.send(ValidationEvent.Error(throwable.localizedMessage))
            }
        }
        useCases.GetUserInfo(formState.email.replace('.', '_')).observeForever {
            state = state.copy(user = it)

            CoroutineScope(Dispatchers.Default).launch(handler) {
                loginData()
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

        formState = formState.copy(
            emailError = emailResult.errorMessage,
            passwordError = passwordResult.errorMessage
        )
        return !hasError
    }

    private suspend fun loginData() {
        val valid = state.user!!.pwd == formState.password
        if (valid) {
            validateEventChannel.send(ValidationEvent.Success)
        } else {
            formState = formState.copy(passwordError = "비밀번호가 일치하지 않습니다.")
        }
    }

    fun clearForm() {
        formState = formState.copy(
            email = "",
            password = ""
        )
        clearError()
    }

    private fun clearError() {
        formState = formState.copy(
            emailError = null,
            passwordError = null
        )
    }
}