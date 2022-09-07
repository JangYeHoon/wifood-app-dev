package com.example.wifood.presentation.view.mypage

import androidx.lifecycle.ViewModel
import com.example.wifood.domain.usecase.WifoodUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    val useCases: WifoodUseCases
) : ViewModel() {

}