package com.example.wifood.presentation.view.mypage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wifood.WifoodApp
import com.example.wifood.domain.model.User
import com.example.wifood.domain.usecase.WifoodUseCases
import com.example.wifood.presentation.view.login.SignUpState
import com.example.wifood.presentation.view.login.util.SignUpData
import com.example.wifood.presentation.view.login.util.ValidationEvent
import com.example.wifood.presentation.view.login.util.ViewItem
import com.example.wifood.presentation.view.main.util.MainData
import com.example.wifood.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    val useCases: WifoodUseCases
) : ViewModel() {

    private val _state = mutableStateOf(
        MyPageState(
            currentUser = MainData.user,
            nickname = MainData.user.nickname
        )
    )
    val state: State<MyPageState> = _state

    private val validateEventChannel = Channel<ValidationEvent>()
    val validationEvents = validateEventChannel.receiveAsFlow()

    fun onEvent(event: MyPageEvent) {
        when (event) {
            is MyPageEvent.NicknameChanged -> {
                _state.value = state.value.copy(nickname = event.nickname)
            }
            is MyPageEvent.DeleteUser -> {
                WifoodApp.pref.setString("Initial_Flag", "0")
                viewModelScope.launch {
                    useCases.DeleteUser(MainData.user.phoneNumber)
                    validateEventChannel.send(ValidationEvent.Success)
                }
            }
            is MyPageEvent.PhoneNumChanged -> {
                _state.value = state.value.copy(phoneNumber = event.phoneNumber)
            }
            is MyPageEvent.AddressChanged -> {
                _state.value = state.value.copy(
                    address = event.address
                )
            }
            is MyPageEvent.ButtonClicked -> {
                try {
                    if (_state.value.address.isNotBlank()) {
                        useCases.GetTMapSearchAddressResult(
                            _state.value.address
                        ).observeForever {
                            try {
                                _state.value = state.value.copy(
                                    searchResults = it
                                )
                            } catch (e: ConcurrentModificationException) {
                            }
                        }
                    }
                } catch (e: NullPointerException) {
                    _state.value = state.value.copy(
                        searchResults = emptyList()
                    )
                }
            }
            is MyPageEvent.AddressClicked -> {
                MainData.user = MainData.user.copy(address = event.address ?: _state.value.address)
            }
            is MyPageEvent.ModifyUserInfo -> {
                if (event.obj == "PHONE") {
                    WifoodApp.pref.setString("user_id", _state.value.phoneNumber)
                    MainData.pre = MainData.user.phoneNumber
                }

                MainData.user = User(
                    phoneNumber = if (event.obj == "PHONE") _state.value.phoneNumber else MainData.user.phoneNumber,
                    address = MainData.user.address,
                    birthday = MainData.user.birthday,
                    gender = MainData.user.gender,
                    nickname = MainData.user.nickname,
                    groupList = MainData.user.groupList,
                    taste = MainData.user.taste
                )

                // TODO : 핸드폰 번호 중복 체크

                viewModelScope.launch {
                    useCases.InsertUser(MainData.user).collectLatest { result ->
                        when (result) {
                            is Resource.Success -> {
                                validateEventChannel.send(ValidationEvent.Success)
                                _state.value = state.value.copy(isLoading = false)
                            }
                            is Resource.Loading -> {
                                _state.value = state.value.copy(isLoading = true)
                            }
                            is Resource.Error -> {
                                validateEventChannel.send(
                                    ValidationEvent.Error(
                                        result.message ?: "Unkwoun Error"
                                    )
                                )
                                _state.value = state.value.copy(isLoading = false)
                            }
                        }
                    }
                }
            }
        }
    }
}