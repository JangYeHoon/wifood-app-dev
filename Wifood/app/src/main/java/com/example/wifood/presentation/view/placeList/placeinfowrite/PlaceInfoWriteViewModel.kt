package com.example.wifood.presentation.view.placeList.placeinfowrite

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.wifood.WifoodApp
import com.example.wifood.data.remote.dto.PlaceDto
import com.example.wifood.domain.model.MenuGrade
import com.example.wifood.domain.model.Place
import com.example.wifood.domain.model.TMapSearch
import com.example.wifood.domain.usecase.WifoodUseCases
import com.example.wifood.presentation.util.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
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
    var state by mutableStateOf(PlaceInfoWriteState())
    var formState by mutableStateOf(PlaceInfoWriteFormState())
    private val validateEventChannel = Channel<ValidationEvent>()
    val validationEvents = validateEventChannel.receiveAsFlow()

    init {
        savedStateHandle.get<Place>("place")?.let { place ->
            state = state.copy(place = place)
            setFormInputValueToPlaceEntity(state.place)
            setStarEnableToSelectedIdx(state.place.score.toInt() - 1)
            if (place.placeId != -1) {
                formState = formState.copy(placeEditChk = true)
                useCases.GetPlaceImageUris(place.groupId, place.placeId).observeForever { uris ->
                    formState = formState.copy(placeImages = uris as ArrayList<Uri>)
                    Timber.i("get image uri list from firebase : " + formState.placeImages.toString())
                }
            }
        }

        useCases.GetGroups().observeForever {
            formState = formState.copy(groups = it)
            it.find { group -> group.groupId == state.place.groupId }?.let { findGroup ->
                formState = formState.copy(groupName = findGroup.name)
                state = state.copy(group = findGroup)
            }
            Timber.i("get groups from firebase $it")
        }
    }

    private fun setFormInputValueToPlaceEntity(place: Place) {
        formState = formState.copy(
            menuGrades = place.menuList as ArrayList<MenuGrade>,
            cleanChk = place.cleanChk,
            kindChk = place.kindChk,
            tasteChk = place.tasteChk,
            vibeChk = place.vibeChk,
            review = place.review,
            score = place.score,
            visited = place.visited,
            placeName = place.name,
            latitude = place.latitude,
            longitude = place.longitude,
            address = place.address,
            reviewTextLength = place.review.length.toString() + "/200"
        )
    }

    @DelicateCoroutinesApi
    suspend fun onEvent(event: PlaceInfoWriteFormEvent) {
        when (event) {
            is PlaceInfoWriteFormEvent.GroupSelected -> {
                formState = formState.copy(groupName = event.group.name)
                state = state.copy(group = event.group)
                state.place.groupId = event.group.groupId
            }
            is PlaceInfoWriteFormEvent.SearchPlaceSelected -> {
                updatePlaceFromSearchTMapAPI(event.searchPlace)
            }
            is PlaceInfoWriteFormEvent.VisitedCheck -> {
                state.place.visited = event.visited
                formState = formState.copy(visited = event.visited)
            }
            is PlaceInfoWriteFormEvent.ScoreChange -> {
                setStarEnableToSelectedIdx(event.selectedStarIdx)
            }
            is PlaceInfoWriteFormEvent.TasteCheck -> {
                state.place.tasteChk = event.tasteChk
                formState = formState.copy(tasteChk = event.tasteChk)
            }
            is PlaceInfoWriteFormEvent.CleanCheck -> {
                state.place.cleanChk = event.cleanChk
                formState = formState.copy(cleanChk = event.cleanChk)
            }
            is PlaceInfoWriteFormEvent.KindCheck -> {
                state.place.kindChk = event.kindChk
                formState = formState.copy(kindChk = event.kindChk)
            }
            is PlaceInfoWriteFormEvent.VibeCheck -> {
                state.place.vibeChk = event.vibeChk
                formState = formState.copy(vibeChk = event.vibeChk)
            }
            is PlaceInfoWriteFormEvent.ReviewChange -> {
                if (event.review.length <= 200) {
                    formState = formState.copy(
                        review = event.review,
                        reviewTextLength = event.review.length.toString() + "/200"
                    )
                    state.place.review = event.review
                }
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
                setPlaceEntityToInputMenu()
                insertPlace()
                insertImages()
            }
            is PlaceInfoWriteFormEvent.PlaceEditBtnClick -> {
                setPlaceEntityToInputMenu()
                updatePlace()
                insertImages()
            }
            is PlaceInfoWriteFormEvent.CurrentLocationChange -> {
                formState = formState.copy(currentLocation = event.location)
            }
            is PlaceInfoWriteFormEvent.BackBtnClick -> {
                state = state.copy(place = event.place)
            }
        }
    }

    private fun setStarEnableToSelectedIdx(selectedIdx: Int) {
        val star = mutableListOf(0, 0, 0, 0, 0)
        var score = 0.0f
        for (i: Int in 0..4) {
            if (i <= selectedIdx) {
                star[i] = 1
                score += 1
            } else
                star[i] = 0
        }
        formState = formState.copy(starScore = star, score = score)
        state.place.score = score
    }

    private fun updatePlaceFromSearchTMapAPI(searchPlace: TMapSearch) {
        state.place.name = searchPlace.name
        state.place.latitude = searchPlace.latitude
        state.place.longitude = searchPlace.longitude
        state.place.address = searchPlace.fullAddress
        state.place.bizName = searchPlace.bizName
    }

    private fun addMenuGradeAndSetMenuGradeInputEmpty() {
        formState.menuGrades.add(
            MenuGrade(
                state.place.placeId,
                formState.menuName,
                formState.menuPrice.toInt(),
                formState.menuMemo
            )
        )
        state.place.menuList = formState.menuGrades
        formState = formState.copy(menuName = "")
        formState = formState.copy(menuPrice = "")
        formState = formState.copy(menuMemo = "")
    }

    private fun setPlaceEntityToInputMenu() {
        state.place.menu = getMenuStringFromMenuGradeListName()
        state.place.menuList = formState.menuGrades
    }

    private fun getMenuStringFromMenuGradeListName(): String {
        var menu = ""
        formState.menuGrades.forEachIndexed { index, menuGrade ->
            menu +=
                if (index != formState.menuGrades.lastIndex)
                    menuGrade.name + ", "
                else
                    menuGrade.name
        }
        return menu
    }

    private fun insertPlace() {
        state.place.placeId = WifoodApp.pref.getInt("place_max_id", -1) + 1
        Timber.i("insert place to firebase : ${state.place}")
        useCases.InsertPlace(state.place)
    }

    private fun updatePlace() {
        Timber.i("update place to firebase : ${state.place}")
        useCases.InsertPlace(state.place)
    }

    @DelicateCoroutinesApi
    private suspend fun insertImages() {
        if (formState.placeImages.isNotEmpty()) {
            useCases.InsertPlaceImages(
                state.place.groupId,
                state.place.placeId,
                formState.placeImages
            ).addOnSuccessListener {
                GlobalScope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        validateEventChannel.send(ValidationEvent.Success)
                    }
                }
                formState = formState.copy(isLoading = false)
            }.addOnProgressListener {
                Timber.i("image upload progress")
                formState = formState.copy(
                    isLoading = true
                )
            }
        } else
            validateEventChannel.send(ValidationEvent.Success)
    }

    fun getPictureIntent(context: Context): Intent {
        val fullSizeCaptureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val photoFile: File = File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            formState = formState.copy(currentPhotoPath = absolutePath)
        }
        photoFile.also {
            val photoUri =
                FileProvider.getUriForFile(
                    context,
                    "com.example.wifood.fileprovider",
                    it
                )
            fullSizeCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        }
        return fullSizeCaptureIntent
    }
}