package com.example.wifood.presentation.view.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.wifood.domain.usecase.WifoodUseCases
import com.example.wifood.presentation.view.login.util.ViewItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 새로운 로그인 프로세스 로직을 위한 기능 개발용 임시 뷰모델 클래스입니다.
 * 테스트용이며, 네이밍은 추후 변경가능.
 */
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val useCases: WifoodUseCases
) : ViewModel() {
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
            }
            is SignUpEvent.CertChanged -> {
                _state.value = SignUpState(
                    certNumber = event.certNumber
                )
            }
            is SignUpEvent.AddressChanged -> {
                _state.value = SignUpState(
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
            }
            is SignUpEvent.RequestCertNumber -> {
                /* 서버에 인증번호 요청, 서버에서 사용자 핸드폰 번호로 SMS 전송 */
                tempRequestCertNumber()
            }
            is SignUpEvent.Verify -> {
                /* 서버에 인증번호 전송, 서버에서 결과 응답하면 받아서 처리 */
                tempVerify(event.certNumber)
            }
            is SignUpEvent.ShowDocument -> {
                /* 개인정보처리방침 다운로드 받아서 화면 하나 생성해놓고, 요청 시 보여줌 */
            }
        }
    }

    fun checkForm(view: ViewItem): Boolean {
        when (view) {
            is ViewItem.SignUpView1 -> {
                val phoneResult = useCases.ValidatePhone(_state.value.phoneNumber)

                val hasError = !phoneResult.successful

                _state.value = SignUpState(
                    phoneNumberError = phoneResult.errorMessage
                )

                return !hasError
            }
        }
    }

    private fun tempRequestCertNumber() {

    }

    private fun tempVerify(certNumber: String): Boolean {
        return true
    }
}