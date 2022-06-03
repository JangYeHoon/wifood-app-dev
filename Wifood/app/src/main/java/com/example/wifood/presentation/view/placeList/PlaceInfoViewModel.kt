package com.example.wifood.presentation.view.placeList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.wifood.domain.model.Place
import com.example.wifood.domain.usecase.WifoodUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// @HiltViewModel : 의존성 주입을 위한 어노테이션 선언
// @Inject constructor : 해당 클래스는 의존성 주입을 해달라고 선언
// 일단은 PlaceInfoViewModel 이라고 해뒀는데 한 가지 뷰모델이 비슷한 기능으로 여러 곳에서 쓰이면 합쳐도 됨(재활용 가능)
@HiltViewModel
class PlaceInfoViewModel @Inject constructor(
    private val useCases: WifoodUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    // state
    var state by mutableStateOf(PlaceInfoState())

    init {
        // 전 화면(그룹리스트)에서 이 화면으로 클릭된 place가 어떤 place인지 알만한 정보를 넘겨줘야 함
        // 그 때 넘겨준 정보가 savedStateHandle에 담겨있음
        // 어떤 데이터 형태로 넘겨줄지는 결정해야 함
        // 아래는 예시
        savedStateHandle.get<Place>("place")?.let { place ->
            state = state.copy(placeInfo = place)
        }
    }

    // event
    fun onEvent(event: PlaceInfoEvent) {
        when (event) {
            is PlaceInfoEvent.SomeEvent -> {

            }
            is PlaceInfoEvent.OtherEvent -> {

            }
        }
    }

    // Ui 관련된 이벤트가 필요하면 여기로 따로 빼야 함
    // 없을 수도 있어서 없으면 삭제
    // ex) Toast 메세지 띄우기, Snackbar 띄우기, 지도 다시 그리기 등
    fun onUiEvent(event: UiEvent) {

    }
}

// Ui 관련된 이벤트가 필요하면 여기로 따로 빼야 함
// 없을 수도 있어서 없으면 삭제
sealed class UiEvent {
    data class ShowSnackBar(val message: String) : UiEvent()
}