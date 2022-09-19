package com.example.wifood.data.remote

import android.graphics.Bitmap
import android.location.Location
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.toolbox.HttpResponse
import com.example.wifood.WifoodApp
import com.example.wifood.data.remote.dto.*
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place
import com.example.wifood.domain.model.User
import com.example.wifood.domain.model.TMapSearch
import com.example.wifood.presentation.view.main.util.MainData
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.poi_item.TMapPOIItem
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import timber.log.Timber
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlin.concurrent.thread

class WifoodApiImpl @Inject constructor(
    private val db: DatabaseReference,
    private val client: HttpClient
) : WifoodApi {
    val id = WifoodApp.pref.getString("user_id", "No user data").replace('.', '_')

    override fun getGroups(): LiveData<MutableList<Group>> {
        val groupList = MutableLiveData<MutableList<Group>>()
        db.child(id).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list: MutableList<Group> = mutableListOf()
                if (snapshot.exists()) {
                    for (groupId in snapshot.child("Group").children) {
                        val group = groupId.getValue(GroupDto::class.java)!!.toGroup()
                        list.add(group)
                        groupList.value = list
                    }
                } else groupList.postValue(null)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return groupList
    }

    override fun deleteGroup(groupId: Int) {
        Timber.i("delete group : groupId-$groupId")
        db.child("$id/Group/$groupId").removeValue()
            .addOnSuccessListener { Timber.i("Success group delete") }
            .addOnFailureListener { Timber.e("Fail group delete : $it") }
    }

    override fun insertGroup(group: Group) {
        db.child(id).child("Group").child(group.groupId.toString())
            .setValue(group)
            .addOnSuccessListener { Timber.i("Success group insert") }
            .addOnFailureListener { Timber.e("Fail group insert : $it") }
    }

    override fun updateGroup(group: Group) {
        val groupPath =
            db.child(id).child("Group").child(group.groupId.toString())
        groupPath.child("groupId").setValue(group.groupId)
        groupPath.child("name").setValue(group.name)
        groupPath.child("description").setValue(group.description)
        groupPath.child("color").setValue(group.color)
        Timber.i("Firebase group update : $group")
    }

    override fun getPlaceList(): LiveData<MutableList<Place>> {
        val placeList = MutableLiveData<MutableList<Place>>()
        db.child("Place").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list: MutableList<Place> = mutableListOf()
                if (snapshot.exists()) {
                    for (PlaceSnapshot in snapshot.children) {
                        val place = PlaceSnapshot.getValue(Place::class.java)
                        list.add(place!!)
                        placeList.value = list
                    }
                } else placeList.postValue(null)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return placeList
    }

    override fun getPlaceImageUris(groupId: Int, placeId: Int): LiveData<MutableList<Uri>> {
        // TODO "싱글톤에 지정해서 storage를 인자로 받도록 변경 필요"
        val storage = FirebaseStorage.getInstance().reference
        val imageUrisForObserve = MutableLiveData<MutableList<Uri>>()
        storage.child("$id/$groupId/$placeId/").listAll()
            .addOnSuccessListener {
                val uris: MutableList<Uri> = mutableListOf()
                for (item in it.items) {
                    item.downloadUrl.addOnCompleteListener { url ->
                        if (url.isSuccessful) {
                            uris.add(url.result)
                            imageUrisForObserve.value = uris
                        }
                        Timber.i("get image url list from Firebase Storage : " + imageUrisForObserve.value.toString())
                    }
                }
            }
        return imageUrisForObserve
    }

    override fun getPlaceImageUri(groupId: Int, placeId: Int): LiveData<Uri> {
        val storage = FirebaseStorage.getInstance().reference
        val imageUriForObserve = MutableLiveData<Uri>()
        storage.child("$id/$groupId/$placeId/0").downloadUrl.addOnCompleteListener { url ->
            if (url.isSuccessful) {
                imageUriForObserve.value = url.result
                Timber.i("get image url list from Firebase Storage : " + imageUriForObserve.value.toString())
            }
        }
        return imageUriForObserve
    }

    override fun insertPlace(place: Place) {
        db.child(id).child("Group").child(place.groupId.toString()).child("Place")
            .child(place.placeId.toString()).setValue(place)
            .addOnSuccessListener { Timber.i("Success place insert") }
            .addOnFailureListener { Timber.e("Fail place insert : $it") }
    }

    override fun deletePlace(groupId: Int, placeId: Int) {
        Timber.i("delete place : groupId-$groupId, placeId-$placeId")
        db.child("$id/Group/$groupId/Place/$placeId").removeValue()
            .addOnSuccessListener { Timber.i("Success place delete") }
            .addOnFailureListener { Timber.e("Fail place delete : $it") }
    }

    override fun updatePlace(place: Place) {
        val placePath =
            db.child(id).child("Group").child(place.groupId.toString()).child("Place")
                .child(place.placeId.toString())
        placePath.child("review").setValue(place.review)
    }

    override fun getUserAllData(id: String): LiveData<User> {
        val user = MutableLiveData<User>()
        db.child(id).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val userDto = snapshot.getValue(UserDto::class.java)
                    userDto!!.groupList =
                        snapshot.child("Group").children.map {
                            it.getValue(GroupDto::class.java)!!
                        }
                    userDto.taste = snapshot.child("Taste").getValue(TasteDto::class.java)
                    for (group in userDto.groupList) {
                        group.placeList =
                            snapshot.child("Group/${group.groupId}/Place").children.map {
                                it.getValue(PlaceDto::class.java)!!
                            }
                    }
                    user.postValue(
                        userDto.toUser()
                    )
                } else user.postValue(null)
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.e("Firebase cancelled")
            }
        })

        return user
    }

    override fun deleteUser(id: String) {
        db.child(id).removeValue()
    }

    override fun checkUser(id: String): LiveData<Int> {
        val result = MutableLiveData<Int>()

        result.postValue(2)

        db.child(id).get().addOnSuccessListener {
            if (it.exists()) {
                result.postValue(1)
            } else {
                result.postValue(-1)
            }
        }.addOnFailureListener {
            result.postValue(0)
        }
        return result
    }

    override fun getUserInfo(id: String): LiveData<User> {
        val user = MutableLiveData<User>()
        db.child(id).get().addOnSuccessListener {
            if (it.exists()) {
                val userDto = it.getValue(UserDto::class.java)
                user.postValue(userDto?.toUser())
            } else {
                user.postValue(null)
            }
        }
        return user
    }

    override fun checkNickname(nickname: String): Boolean {
        return true
    }

    override fun insertUser(user: User) {
        db.child(user.phoneNumber).setValue(user)

        db.child(MainData.pre).removeValue()
    }

    override fun insertPlaceImages(
        groupId: Int,
        placeId: Int,
        images: ArrayList<Uri>
    ): UploadTask {
        val storage = FirebaseStorage.getInstance().reference
        var uploadTask: UploadTask? = null
        images.forEachIndexed { index, uri ->
            uploadTask = storage.child("$id/$groupId/$placeId/").child("$index").putFile(uri)
        }
        return uploadTask!!
    }

    override fun getTMapSearchPlaceResult(
        keyword: String,
        currentLocation: Location
    ): LiveData<MutableList<TMapSearch>> {
        val tmapSearchResult = MutableLiveData<MutableList<TMapSearch>>()
        val tmapData = TMapData()
        thread(start = true) {
            try {
                val tMapItems = tmapData.findAroundKeywordPOI(
                    TMapPoint(currentLocation.latitude, currentLocation.longitude),
                    keyword,
                    0,
                    50
                )
                val tempList: MutableList<TMapSearch> = mutableListOf()
                for (searchResult in tMapItems) {
                    val bizName: String =
                        searchResult.middleBizName.toString() + "," + searchResult.lowerBizName
                    var addressRoad = ""
                    for (address in searchResult.newAddressList)
                        addressRoad = address.fullAddressRoad
                    addressRoad += searchResult.detailAddrName.replace("null", "")
                    tempList.add(
                        TMapSearch(
                            addressRoad,
                            searchResult.poiName.toString(),
                            searchResult.poiPoint.latitude,
                            searchResult.poiPoint.longitude,
                            bizName
                        )
                    )
                    tmapSearchResult.postValue(tempList)
                }
            } catch (e: Exception) {
                tmapSearchResult.postValue(arrayListOf(TMapSearchDto().toTMapSearch()))
            }
        }
        return tmapSearchResult
    }

    override fun getTMapSearchAddressResult(keyword: String): LiveData<MutableList<TMapSearch>> {
        val tmapSearchResult = MutableLiveData<MutableList<TMapSearch>>()
        val tmapData = TMapData()
        val tempList: MutableList<TMapSearch> = mutableListOf()
        thread(start = true) {
            try {
                val tMapItems = tmapData.findAddressPOI(keyword, 50)
                tMapItems.forEach { searchResult ->
                    searchResult.poiName
                    val bizName: String =
                        searchResult.middleBizName.toString() + "," + searchResult.lowerBizName
                    val address = searchResult.poiAddress.replace("null", "")
                    tempList.add(
                        TMapSearch(
                            address,
                            searchResult.poiName,
                            searchResult.poiPoint.latitude,
                            searchResult.poiPoint.longitude,
                            bizName
                        )
                    )
                    tmapSearchResult.postValue(tempList)
                }
            } catch (e: Exception) {
                tmapSearchResult.postValue(arrayListOf())
            }
        }
        return tmapSearchResult
    }

    override fun getTMapSearchDetailAddressResult(keyword: String): LiveData<MutableList<TMapSearch>> {
        val tmapSearchResult = MutableLiveData<MutableList<TMapSearch>>()
        val tmapData = TMapData()
        val tempList: MutableList<TMapSearch> = mutableListOf()
        thread(start = true) {
            try {
                val tMapItems = tmapData.findAddressPOI(keyword, 50)
                tMapItems.forEach { searchResult ->
                    searchResult.poiName
                    val bizName: String =
                        searchResult.middleBizName.toString() + "," + searchResult.lowerBizName
                    var addressRoad = ""
                    for (address in searchResult.newAddressList)
                        addressRoad = address.fullAddressRoad
                    addressRoad += searchResult.detailAddrName.replace("null", "")
                    var oldAddress = searchResult.poiAddress.replace("null", "")
                    oldAddress += "" + searchResult.firstNo
                    if (searchResult.secondNo != "0" && searchResult.secondNo.isNotEmpty())
                        oldAddress += "-" + searchResult.secondNo
                    tempList.add(
                        TMapSearch(
                            addressRoad,
                            searchResult.poiName,
                            searchResult.poiPoint.latitude,
                            searchResult.poiPoint.longitude,
                            bizName,
                            oldAddress
                        )
                    )
                    tmapSearchResult.postValue(tempList)
                }
            } catch (e: Exception) {
                Timber.e(e.toString())
                tmapSearchResult.postValue(arrayListOf())
            }
        }
        return tmapSearchResult
    }

    override fun getTMapReverseGeocoding(latLng: LatLng): LiveData<String> {
        val address = MutableLiveData<String>()
        val tmapData = TMapData()
        thread(start = true) {
            try {
                val tMapAddressInfo =
                    tmapData.reverseGeocoding(latLng.latitude, latLng.longitude, "A10")
                val firstAddress = tMapAddressInfo.strCity_do + " " + tMapAddressInfo.strGu_gun
                var roadAddress =
                    firstAddress + " " + tMapAddressInfo.strRoadName + " " + tMapAddressInfo.strBuildingIndex
                if (tMapAddressInfo.strBuildingName.isNotEmpty())
                    roadAddress += " (" + tMapAddressInfo.strBuildingName + ")"
                var oldAddress =
                    firstAddress + " " + tMapAddressInfo.strLegalDong
                if (tMapAddressInfo.strRi.isNotEmpty())
                    oldAddress += " " + tMapAddressInfo.strRi
                oldAddress += " " + tMapAddressInfo.strBunji
                address.postValue("$roadAddress/$oldAddress")
            } catch (e: Exception) {
                Timber.e(e.message)
            }
        }
        return address
    }

    override suspend fun requestCertNumber(phoneNumber: String): String {
        val response = client.post<Response> {
            url("http://192.168.0.8:8080/sms")
            contentType(ContentType.Application.Json)
            body = Request(phoneNumber)
        }

        return response.message
    }
}