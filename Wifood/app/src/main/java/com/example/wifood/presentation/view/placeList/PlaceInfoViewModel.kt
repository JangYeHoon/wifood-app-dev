package com.example.wifood.presentation.view.placeList

import android.util.Log
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
        // PlaceList에서 받은 place 정보
        savedStateHandle.get<Place>("place")?.let { place ->
            state = state.copy(placeInfo = place)
        }

        // PlaceList에서 받은 groupNmae 정보
        savedStateHandle.get<String>("groupName")?.let { groupName ->
            state = state.copy(groupName = groupName)
        }

        Log.d(
            "PlaceInfoViewModel",
            "get place info from PlaceList : " + state.placeInfo.toString() + ", " + state.groupName
        )

        // Firebase storage에서 해당 place에 저장된 이미지 리스트 받아와서 저장
        state.placeInfo?.let {
            useCases.GetPlaceImageList(0, 0).observeForever { uriList ->
                state = state.copy(imageUri = uriList)
                Log.d(
                    "PlaceInfoViewModel",
                    "get image uri list from firebase : " + state.imageUri.toString()
                )
            }
        }
    }

    // event
    fun onEvent(event: PlaceInfoEvent) {
        when (event) {
            is PlaceInfoEvent.PlaceDeleteEvent -> {
                useCases.DeletePlace(state.placeInfo!!.groupId, state.placeInfo!!.placeId)
            }
        }
    }
}