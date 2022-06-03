package com.example.wifood.presentation.view.placeList

// Event sealed class : 화면에서 유저에 의해 발생할 수 있는 이벤트들을 나열해 놓음
// 그래서 생각해보고 필요한 것을 여기다 넣으면 됨
// 아래는 내가 예시로 대충 넣어놓은 것, 필요에 따라 수정하셈
// 파라미터를 넘겨줄 필요가 있는 애들은 data class로 선언
// 파라미터 필요없으면 object로 선언
sealed class PlaceInfoEvent {
    data class SomeEvent(val data: Unit) : PlaceInfoEvent()
    object OtherEvent : PlaceInfoEvent()
}
