package com.example.wifood.presentation.view.placeList

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wifood.WifoodApp
import com.example.wifood.data.remote.dto.PlaceDto
import com.example.wifood.domain.model.MenuGrade
import com.example.wifood.domain.model.User
import com.example.wifood.domain.usecase.WifoodUseCases
import com.example.wifood.presentation.util.ValidationEvent
import com.example.wifood.util.Resource
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class PlaceInfoWriteViewModel @Inject constructor(
    private val useCases: WifoodUseCases,
    @ApplicationContext applicationContext: Context,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val field = listOf(Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS)
    var state by mutableStateOf(PlaceInfoWriteState())
    var formState by mutableStateOf(PlaceInfoWriteFormState())
    private val validateEventChannel = Channel<ValidationEvent>()
    val validationEvents = validateEventChannel.receiveAsFlow()

    init {
        Places.initialize(applicationContext, "AIzaSyB_HZJANQB8zVtH33wHb2OI-FbeDhPYRtA")

        val place = savedStateHandle.get<com.example.wifood.domain.model.Place>("place")
        if (place!!.name.isNotEmpty()) {
            state = state.copy(place = place)
            formState = formState.copy(
                menuGrades = state.place!!.menuList as ArrayList<MenuGrade>,
                menu = state.place!!.menu,
                cleanChk = state.place!!.cleanChk,
                kindChk = state.place!!.kindChk,
                tasteChk = state.place!!.tasteChk,
                vibeChk = state.place!!.vibeChk,
                review = state.place!!.review,
                score = state.place!!.score,
                visited = state.place!!.visited,
                placeName = state.place!!.name
            )
            useCases.GetPlaceImageUris(place.groupId, place.placeId).observeForever { uris ->
                formState = formState.copy(placeImages = uris as ArrayList<Uri>)
                Timber.i("get image uri list from firebase : " + formState.placeImages.toString())
            }
        } else {
            state = state.copy(place = place)
            state.place!!.placeId = WifoodApp.pref.getInt("place_max_id", -1) + 1
        }

        useCases.GetGroups().observeForever {
            formState = formState.copy(groups = it)
            for (group in it) {
                if (state.place!!.groupId == group.groupId)
                    formState = formState.copy(groupName = group.name)
            }
            Timber.i("get groups from firebase $it")
        }
    }

    suspend fun onEvent(event: PlaceInfoWriteFormEvent) {
        when (event) {
            is PlaceInfoWriteFormEvent.GroupSelected -> {
                formState = formState.copy(groupName = event.group.name)
                state.place!!.groupId = event.group.groupId
            }
            is PlaceInfoWriteFormEvent.PlaceSelected -> {
                updatePlaceFromSearchGoogleAPI(event.searchPlace)
            }
            is PlaceInfoWriteFormEvent.MenuChange -> {
                formState = formState.copy(menu = event.menu)
            }
            is PlaceInfoWriteFormEvent.VisitedCheck -> {
                formState = formState.copy(visited = event.visited)
            }
            is PlaceInfoWriteFormEvent.ScoreChange -> {
                formState = formState.copy(score = event.score)
            }
            is PlaceInfoWriteFormEvent.TasteCheck -> {
                formState = formState.copy(tasteChk = event.tasteChk)
            }
            is PlaceInfoWriteFormEvent.CleanCheck -> {
                formState = formState.copy(cleanChk = event.cleanChk)
            }
            is PlaceInfoWriteFormEvent.KindCheck -> {
                formState = formState.copy(kindChk = event.kindChk)
            }
            is PlaceInfoWriteFormEvent.VibeCheck -> {
                formState = formState.copy(vibeChk = event.vibeChk)
            }
            is PlaceInfoWriteFormEvent.ReviewChange -> {
                formState = formState.copy(review = event.review)
            }
            is PlaceInfoWriteFormEvent.MenuNameChange -> {
                formState = formState.copy(menuName = event.menuName)
            }
            is PlaceInfoWriteFormEvent.MenuPriceChange -> {
                formState = formState.copy(menuPrice = event.menuPrice)
            }
            is PlaceInfoWriteFormEvent.MenuMemoChange -> {
                formState = formState.copy(menuMemo = event.menuMemo)
            }
            is PlaceInfoWriteFormEvent.PlaceImagesAdd -> {
                formState.placeImages.add(event.image)
            }
            is PlaceInfoWriteFormEvent.ImageNameChange -> {
                formState = formState.copy(currentPhotoPath = event.imageName)
            }
            is PlaceInfoWriteFormEvent.MenuGradeAddBtnClick -> {
                if (formState.menuName.isNotEmpty())
                    addMenuGradeAndSetMenuGradeInputEmpty()
            }
            is PlaceInfoWriteFormEvent.PlaceAddBtnClick -> {
                if (formCheck()) {
                    insertPlace()
                    insertImages()
                }
            }
        }
    }

    private fun updatePlaceFromSearchGoogleAPI(searchPlace: Place) {
        formState = formState.copy(placeName = searchPlace.name)
        setPlaceEntityToSearchPlace(searchPlace)
    }

    private fun setPlaceEntityToSearchPlace(searchPlace: Place) {
        state.place!!.name = searchPlace.name
        state.place!!.latitude = searchPlace.latLng.latitude
        state.place!!.longitude = searchPlace.latLng.longitude
        state.place!!.address = searchPlace.address
    }

    private fun addMenuGradeAndSetMenuGradeInputEmpty() {
        formState.menuGrades.add(
            MenuGrade(
                state.place!!.placeId,
                formState.menuName,
                formState.menuPrice.toInt(),
                formState.menuMemo
            )
        )
        formState = formState.copy(menuName = "")
        formState = formState.copy(menuPrice = "")
        formState = formState.copy(menuMemo = "")
    }

    private fun formCheck(): Boolean {
        return formState.groupName != "그룹 선택" && formState.placeName != "맛집 선택"
    }

    private fun insertPlace() {
        state.place!!.menuList = formState.menuGrades
        state.place!!.menu = formState.menu
        state.place!!.cleanChk = formState.cleanChk
        state.place!!.kindChk = formState.kindChk
        state.place!!.tasteChk = formState.tasteChk
        state.place!!.vibeChk = formState.vibeChk
        state.place!!.review = formState.review
        state.place!!.score = formState.score
        state.place!!.visited = formState.visited

        Timber.i("insert place to firebase : ${state.place}")
        useCases.InsertPlace(state.place!!)
    }

    private suspend fun insertImages() {
        useCases.InsertPlaceImages(
            state.place!!.groupId,
            state.place!!.placeId,
            formState.placeImages
        )

        validateEventChannel.send(ValidationEvent.Success)
    }
}