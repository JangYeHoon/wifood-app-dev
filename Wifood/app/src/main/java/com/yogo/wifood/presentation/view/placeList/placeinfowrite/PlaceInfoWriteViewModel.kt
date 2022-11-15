package com.yogo.wifood.presentation.view.placeList.placeinfowrite

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
import com.yogo.wifood.WifoodApp
import com.yogo.wifood.domain.model.MenuGrade
import com.yogo.wifood.domain.model.Place
import com.yogo.wifood.domain.model.TMapSearch
import com.yogo.wifood.domain.usecase.WifoodUseCases
import com.yogo.wifood.presentation.util.ValidationEvent
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
            if (formState.menuGrades.isEmpty()) {
                formState.menuGrades.add(MenuGrade(state.place.placeId, "", 0, ""))
            }
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
                formState = formState.copy(menuName = if (formState.menuName == "") "1" else "")
                formState.menuGrades[event.idx].name = event.menuName
                state.place.menuList[event.idx].name = event.menuName
            }
            is PlaceInfoWriteFormEvent.MenuPriceChange -> {
                try {
                    if (event.menuPrice.isEmpty()) {
                        formState.menuGrades[event.idx].price = 0
                        state.place.menuList[event.idx].price = 0
                    } else {
                        formState.menuGrades[event.idx].price = event.menuPrice.toInt()
                        state.place.menuList[event.idx].price = event.menuPrice.toInt()
                    }
                } catch (e: NumberFormatException) {
                    showSnackBar("숫자만 입력해주세요.")
                } finally {
                    formState =
                        formState.copy(menuPrice = if (formState.menuPrice == "") "1" else "")
                }
            }
            is PlaceInfoWriteFormEvent.MenuMemoChange -> {
                formState = formState.copy(menuMemo = if (formState.menuMemo == "") "1" else "")
                formState.menuGrades[event.idx].memo = event.menuMemo
                state.place.menuList[event.idx].memo = event.menuMemo
            }
            is PlaceInfoWriteFormEvent.PlaceImagesAdd -> {
                formState.placeImages.add(event.image)
                formState =
                    formState.copy(
                        placeImagesReCompose =
                        if (formState.placeImagesReCompose == "1")
                            "2"
                        else
                            "1",
                        imageUploadChk = true
                    )
                Timber.i(formState.placeImages.toString())
            }
            is PlaceInfoWriteFormEvent.ImageNameChange -> {
                formState = formState.copy(currentPhotoPath = event.imageName)
            }
            is PlaceInfoWriteFormEvent.MenuGradeAddBtnClick -> {
                addMenuGradeAndSetMenuGradeInputEmpty()
            }
            is PlaceInfoWriteFormEvent.PlaceAddBtnClick -> {
                setPlaceEntityToInputMenu()
                state.place.deleteMenuGradeFromEmptyName()
                insertPlace()
                insertImages()
            }
            is PlaceInfoWriteFormEvent.PlaceEditBtnClick -> {
                setPlaceEntityToInputMenu()
                state.place.deleteMenuGradeFromEmptyName()
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
                "",
                0,
                ""
            )
        )
        state.place.menuList = formState.menuGrades
        formState = formState.copy(menuName = if (formState.menuName == "") "1" else "")
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
        state.place.placeId = com.yogo.wifood.WifoodApp.pref.getInt("place_max_id", -1)
        Timber.i("insert place to firebase : ${state.place}")
        useCases.InsertPlace(state.place)
    }

    private fun updatePlace() {
        Timber.i("update place to firebase : ${state.place}")
        useCases.InsertPlace(state.place)
    }

    fun checkForm(): Boolean {
        formState.menuGrades.forEach {
            if ((it.price != 0 || it.memo.isNotEmpty()) && it.name.isEmpty()) {
                showSnackBar("메뉴명을 입력해주세요.")
                return false
            }
        }
        return true
    }

    private fun showSnackBar(message: String) {
        viewModelScope.launch {
            validateEventChannel.send(ValidationEvent.Error(message))
        }
    }

    @DelicateCoroutinesApi
    private suspend fun insertImages() {
        if (formState.placeImages.isNotEmpty() && formState.imageUploadChk) {
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
                    "com.yogo.wifood.fileprovider",
                    it
                )
            fullSizeCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        }
        return fullSizeCaptureIntent
    }
}