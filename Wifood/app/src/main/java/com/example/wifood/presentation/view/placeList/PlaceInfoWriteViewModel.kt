package com.example.wifood.presentation.view.placeList

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
import androidx.lifecycle.viewModelScope
import com.example.wifood.WifoodApp
import com.example.wifood.data.remote.dto.PlaceDto
import com.example.wifood.domain.model.MenuGrade
import com.example.wifood.domain.usecase.WifoodUseCases
import com.example.wifood.presentation.util.ValidationEvent
import com.example.wifood.util.Resource
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
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
            formState = formState.copy(placeEditChk = true)
            state = state.copy(place = place)
            setFormInputValueToPlaceEntity(state.place)
            useCases.GetPlaceImageUris(place.groupId, place.placeId).observeForever { uris ->
                formState = formState.copy(placeImages = uris as ArrayList<Uri>)
                Timber.i("get image uri list from firebase : " + formState.placeImages.toString())
            }
        } else {
            val maxPlaceId = WifoodApp.pref.getInt("place_max_id", -1) + 1
            state = state.copy(place = PlaceDto(placeId = maxPlaceId).toPlace())
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

    private fun setFormInputValueToPlaceEntity(place: com.example.wifood.domain.model.Place) {
        formState = formState.copy(
            menuGrades = place.menuList as ArrayList<MenuGrade>,
            menu = place.menu,
            cleanChk = place.cleanChk,
            kindChk = place.kindChk,
            tasteChk = place.tasteChk,
            vibeChk = place.vibeChk,
            review = place.review,
            score = place.score,
            visited = place.visited,
            placeName = place.name,
            latLng = LatLng(place.latitude, place.longitude),
            address = place.address,
        )
    }

    @DelicateCoroutinesApi
    suspend fun onEvent(event: PlaceInfoWriteFormEvent) {
        when (event) {
            is PlaceInfoWriteFormEvent.GroupSelected -> {
                formState = formState.copy(groupName = event.group.name)
                state = state.copy(group = event.group)
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
                    setPlaceEntityToFormInput()
                    insertPlace()
                    if (formState.placeImages.isNotEmpty())
                        insertImages()
                }
            }
        }
    }

    private fun updatePlaceFromSearchGoogleAPI(searchPlace: Place) {
        formState = formState.copy(
            placeName = searchPlace.name,
            latLng = searchPlace.latLng,
            address = searchPlace.address
        )
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
        formState = formState.copy(menuName = "")
        formState = formState.copy(menuPrice = "")
        formState = formState.copy(menuMemo = "")
    }

    private fun formCheck(): Boolean {
        return formState.groupName != "그룹 선택" && formState.placeName != "맛집 선택"
    }

    private fun setPlaceEntityToFormInput() {
        state = state.copy(
            place = com.example.wifood.domain.model.Place(
                placeId = state.place.placeId,
                name = formState.placeName,
                groupId = state.group!!.groupId,
                menu = formState.menu,
                visited = formState.visited,
                score = formState.score,
                tasteChk = formState.tasteChk,
                cleanChk = formState.cleanChk,
                kindChk = formState.kindChk,
                vibeChk = formState.vibeChk,
                review = formState.review,
                menuList = formState.menuGrades,
                latitude = formState.latLng.latitude,
                longitude = formState.latLng.longitude,
                address = formState.address,
                imageNameList = emptyList()
            )
        )
    }

    private fun insertPlace() {
        Timber.i("insert place to firebase : ${state.place}")
        useCases.InsertPlace(state.place)
    }

    @DelicateCoroutinesApi
    private suspend fun insertImages() {
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
        }.addOnProgressListener {
            Timber.i("image upload progress")
            formState = formState.copy(
                isLoading = true
            )
        }
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