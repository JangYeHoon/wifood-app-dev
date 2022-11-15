package com.yogo.wifood.presentation.view.mypage

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yogo.wifood.WifoodApp
import com.yogo.wifood.domain.model.Taste
import com.yogo.wifood.domain.model.User
import com.yogo.wifood.domain.usecase.WifoodUseCases
import com.yogo.wifood.presentation.view.login.util.SignUpData
import com.yogo.wifood.presentation.view.login.util.ValidationEvent
import com.yogo.wifood.presentation.view.main.util.MainData
import com.yogo.wifood.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    val useCases: WifoodUseCases
) : ViewModel() {

    private val _state = mutableStateOf(
        if (com.yogo.wifood.WifoodApp.pref.getString("Initial_Flag", "0") == "0") {
            MyPageState(
                currentUser = User(
                    "",
                    "",
                    "",
                    0,
                    ""
                ),
                ""
            )
        } else {
            MyPageState(
                currentUser = MainData.user,
                nickname = MainData.user.nickname,
//                taste = arrayListOf(
//                    MainData.user.taste!!.spicy,
//                    MainData.user.taste!!.salty,
//                    MainData.user.taste!!.acidity,
//                    MainData.user.taste!!.sour,
//                    MainData.user.taste!!.sweet
//                )
            )
        }
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
                com.yogo.wifood.WifoodApp.pref.setString("Initial_Flag", "0")
                viewModelScope.launch {
                    useCases.DeleteUser(MainData.user.phoneNumber)
                    validateEventChannel.send(ValidationEvent.Success)
                }
            }
            is MyPageEvent.PhoneNumChanged -> {
                if (!event.phoneNumber.isDigitsOnly()) {
                    return
                }

                _state.value = state.value.copy(phoneNumber = event.phoneNumber)
            }
            is MyPageEvent.AddressChanged -> {
                _state.value = state.value.copy(
                    address = event.address
                )
            }
            is MyPageEvent.ImageAdd -> {
                _state.value = state.value.copy(
                    image = event.image
                )

//                if (_state.value.placeImagesReCompose == "1") {
//                    _state.value = state.value.copy(
//                        placeImagesReCompose = "2"
//                    )
//                } else {
//                    _state.value = state.value.copy(
//                        placeImagesReCompose = "1"
//                    )
//                }
            }
            is MyPageEvent.ModifyProfile -> {
                viewModelScope.launch {
                    if (MainData.user.nickname != _state.value.nickname) {
                        onEvent(MyPageEvent.ModifyUserInfo("NICKNAME"))
                    }

                    _state.value.image?.let {
                        validateEventChannel.send(ValidationEvent.Loading)
                        useCases.InsertProfile(it, MainData.user.phoneNumber).addOnSuccessListener {
                            GlobalScope.launch(Dispatchers.IO) {
                                withContext(Dispatchers.Main) {
                                    validateEventChannel.send(ValidationEvent.Success)
                                }
                            }
                        }
                    }
                }
            }
            is MyPageEvent.FavorSelected -> {
                val temp = _state.value.taste
                temp.set(event.index, event.selected)

                _state.value = state.value.copy(
                    taste = temp
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
                    com.yogo.wifood.WifoodApp.pref.setString("user_id", _state.value.phoneNumber)
                    MainData.pre = MainData.user.phoneNumber
                }

                MainData.user = User(
                    phoneNumber = if (event.obj == "PHONE") _state.value.phoneNumber else MainData.user.phoneNumber,
                    address = MainData.user.address,
                    birthday = MainData.user.birthday,
                    gender = MainData.user.gender,
                    nickname = if (event.obj == "NICKNAME") _state.value.nickname else MainData.user.nickname,
                    groups = MainData.user.groups,
                    taste = if (event.obj != "TASTE") MainData.user.taste else {
                        Taste(
                            userId = MainData.user.phoneNumber,
                            spicy = _state.value.taste[0],
                            salty = _state.value.taste[2],
                            acidity = _state.value.taste[4],
                            sour = _state.value.taste[3],
                            sweet = _state.value.taste[1],
                            cucumber = SignUpData.cucumberClicked,
                            coriander = SignUpData.corianderClicked,
                            mintChoco = SignUpData.mintChokoClicked,
                            eggplant = SignUpData.eggplantClicked
                        )
                    }
                )

                Log.d("FAVOR", "onEvent: ${_state.value.taste}")
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

    fun checkForm() {
        val phoneResult = useCases.ValidatePhone(_state.value.phoneNumber)

        val hasError = !phoneResult.successful

        if (hasError) {
            _state.value = state.value.copy(
                phoneValidation = false
            )
            return
        }

        _state.value = state.value.copy(
            phoneValidation = true
        )

        if (_state.value.phoneValidation) {
            // 1 = Exist, 2 = Loading, 0 = Error
            useCases.CheckUser(_state.value.phoneNumber).observeForever {
                // TODO
                when (it) {
                    0 -> {

                    }
                    1 -> {

                    }
                    -1 -> {
                        viewModelScope.launch {
                            validateEventChannel.send(ValidationEvent.Success)
                        }
                    }
                    else -> {

                    }
                }

                _state.value = state.value.copy(
                    isLoading = it == 2
                )
            }
        }
    }
}