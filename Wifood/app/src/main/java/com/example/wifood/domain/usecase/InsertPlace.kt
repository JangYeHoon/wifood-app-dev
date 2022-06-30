package com.example.wifood.domain.usecase

import com.example.wifood.domain.model.Place
import com.example.wifood.domain.repository.WifoodRepository
import com.example.wifood.util.Resource
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class InsertPlace @Inject constructor(
    private val repository: WifoodRepository
) {
    operator fun invoke(place: Place) {
        repository.insertPlace(place)
    }
}