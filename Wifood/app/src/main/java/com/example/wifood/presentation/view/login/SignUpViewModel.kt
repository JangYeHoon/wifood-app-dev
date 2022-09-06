package com.example.wifood.presentation.view.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wifood.domain.model.Taste
import com.example.wifood.domain.model.User
import com.example.wifood.domain.usecase.WifoodUseCases
import com.example.wifood.presentation.view.login.util.SignUpData
import com.example.wifood.presentation.view.login.util.ValidationEvent
import com.example.wifood.presentation.view.login.util.ViewItem
import com.example.wifood.util.Resource
import com.navercorp.nid.NaverIdLoginSDK.applicationContext
import com.skt.Tmap.TMapTapi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * 새로운 로그인 프로세스 로직을 위한 기능 개발용 임시 뷰모델 클래스입니다.
 * 테스트용이며, 네이밍은 추후 변경가능.
 */
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val useCases: WifoodUseCases,
    @ApplicationContext applicationContext: Context,
) : ViewModel() {
    private val tMapTapi = TMapTapi(applicationContext)

    /**
     * SignUp 프로세스(로직) 진행 도중 저장해야 할 필요가 있는 데이터(=상태)가 정의된 State 클래스
     * phoneNumber : 사용자의 전화번호
     * phoneNumberError : 전화번호 검증에 대한 에러 핸들링을 위한 프로퍼티
     * address : 사용자의 주소
     * birthday : 사용자의 생년월일
     * birthdayError : 생년월일 검증에 대한 에러 핸들링을 위한 프로퍼티
     * gender : 사용자의 성별
     */
    private val _state = mutableStateOf(SignUpState())
    val state: State<SignUpState> = _state

    private val validateEventChannel = Channel<ValidationEvent>()
    val validationEvents = validateEventChannel.receiveAsFlow()

    init {
        tMapTapi.setSKTMapAuthentication("l7xx56bf2cddf5f84556bdf35558d72f530a")
    }

    /**
     * SignUp 프로세스(로직) 진행 도중 사용자에 의해 발생할 수 있는 이벤트가 정의된 Event 클래스
     * PhoneNumChanged : 사용자가 전화번호 입력란의 값을 변경
     * AddressChanged : 사용자가 주소 입력란의 값을 변경
     * BirthdayChanged : 사용자가 생년월일 입력란의 값을 변경
     * GenderClicked : 사용자가 성별 버튼을 클릭
     * RequestCertNumber : 사용자가 인증번호를 요청
     * Verity : 사용자가 인증번호 검증을 요청
     * ShowDocument : 사용자가 개인정보처리방침 문서를 요청
     */
    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.PhoneNumChanged -> {
                _state.value = SignUpState(
                    phoneNumber = event.phoneNumber
                )
                SignUpData.phoneNumber =
                    if (event.phoneNumber.length >= 11) event.phoneNumber.substring(0..10) else event.phoneNumber
            }
            is SignUpEvent.CertChanged -> {
                _state.value = SignUpState(
                    certNumber = event.certNumber
                )
            }
            is SignUpEvent.AddressChanged -> {
                _state.value = state.value.copy(
                    address = event.address
                )

            }
            is SignUpEvent.BirthdayChanged -> {
                _state.value = SignUpState(
                    birthday = event.birthday
                )
            }
            is SignUpEvent.GenderClicked -> {
                _state.value = SignUpState(
                    gender = !_state.value.gender
                )
                SignUpData.gender = if (_state.value.gender) "남성" else "여성"
            }
            is SignUpEvent.RequestCertNumber -> {
                /* 서버에 인증번호 요청, 서버에서 사용자 핸드폰 번호로 SMS 전송 */
                tempRequestCertNumber(SignUpData.phoneNumber)
            }
            is SignUpEvent.Verify -> {
                /* 서버에 인증번호 전송, 서버에서 결과 응답하면 받아서 처리 */
                tempVerify(event.certNumber, event.timer)
            }
            is SignUpEvent.ShowDocument -> {
                /* 개인정보처리방침 다운로드 받아서 화면 하나 생성해놓고, 요청 시 보여줌 */
            }
            is SignUpEvent.ButtonClicked -> {
                try {
                    if (_state.value.address.isNotBlank()) {
                        useCases.GetTMapSearchAddressResult(
                            _state.value.address
                        ).observeForever {
                            try {
                                _state.value = state.value.copy(
                                    searchResults = it
                                )
                            } catch (e: ConcurrentModificationException) { }
                        }
                    }
                } catch (e: NullPointerException) {
                    _state.value = state.value.copy(
                        searchResults = emptyList()
                    )
                }
            }
            is SignUpEvent.AddressClicked -> {
                SignUpData.address = event.address ?: _state.value.address
            }
            is SignUpEvent.AgreementClicked -> {
                _state.value = SignUpState(
                    agreement = !_state.value.agreement
                )
            }
            is SignUpEvent.FavorSelected -> {
                val temp = _state.value.taste
                temp.set(event.index, event.selected)

                _state.value = state.value.copy(
                    taste = temp
                )
            }
            is SignUpEvent.TasteCreated -> {
                SignUpData.taste = Taste(
                    userId = SignUpData.phoneNumber,
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
                SignUpData.user = User(
                    phoneNumber = SignUpData.phoneNumber,
                    address = SignUpData.address,
                    birthday = SignUpData.birthday,
                    gender = if (SignUpData.gender == "남성") 1 else 0,
                    groupList = emptyList(),
                    taste = SignUpData.taste
                )
                viewModelScope.launch {
                    useCases.InsertUser(SignUpData.user).collectLatest { result ->
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

    private fun showSnackBar(message: String) {
        viewModelScope.launch {
            validateEventChannel.send(ValidationEvent.Error(message))
        }
    }

    fun checkForm(view: ViewItem): Boolean {
        when (view) {
            is ViewItem.SignUpView1 -> {
                val phoneResult = useCases.ValidatePhone(_state.value.phoneNumber)

                val hasError = !phoneResult.successful

                if (hasError)
                    showSnackBar(phoneResult.errorMessage!!)

                return !hasError
            }
        }
    }

    private fun tempRequestCertNumber(phoneNumber: String) {
        useCases.RequestCertNumber(phoneNumber).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = SignUpState(
                        reqCertNumber = result.data,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = SignUpState(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _state.value = SignUpState(
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * 1. 타이머가 0:00 이면 실패
     * 2. 인증번호 검증
     */
    private fun tempVerify(certNumber: String, timer: Int): Boolean {
//        if (timer == 0) {
//            viewModelScope.launch {
//                validateEventChannel.send(ValidationEvent.Error("제한시간이 초과되었습니다. 다시 시도해주세요."))
//            }
//            return false
//        }
//        if (certNumber != _state.value.reqCertNumber) {
//            viewModelScope.launch {
//                validateEventChannel.send(ValidationEvent.Error("인증번호가 일치하지 않습니다."))
//            }
//            return false
//        }
        return true
    }
}