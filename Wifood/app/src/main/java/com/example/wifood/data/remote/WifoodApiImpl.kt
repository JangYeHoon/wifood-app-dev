package com.example.wifood.data.remote

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.data.remote.dto.GroupDto
import com.example.wifood.data.remote.dto.PlaceDto
import com.example.wifood.data.remote.dto.TasteDto
import com.example.wifood.data.remote.dto.UserDto
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place
import com.example.wifood.domain.model.User
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import timber.log.Timber
import javax.inject.Inject

class WifoodApiImpl @Inject constructor(
    private val db: DatabaseReference
) : WifoodApi {
    override fun getGroupList(user: User): LiveData<MutableList<Group>> {
        val groupList = MutableLiveData<MutableList<Group>>()
        val id = user.userId.replace('.', '_')
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
        // TODO "userId를 따로 저장해서 해당 userId를 이용하도록 변경"
        Timber.i("delete group : groupId-$groupId")
        db.child("kmh@naver.com/$groupId").removeValue()
            .addOnSuccessListener { Timber.i("Success group delete") }
            .addOnFailureListener { Timber.e("Fail group delete : $it") }
    }

    override fun insertGroup(group: Group) {
        db.child(group.userId.replace('.', '_')).child("Group").child(group.groupId.toString())
            .setValue(group)
            .addOnSuccessListener { Timber.i("Success group insert") }
            .addOnFailureListener { Timber.e("Fail group insert : $it") }
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
        storage.child("19/").listAll()
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

    override fun deletePlace(groupId: Int, placeId: Int) {
        // TODO "userId를 따로 저장해서 해당 userId를 이용하도록 변경"
        Timber.i("delete place : groupId-$groupId, placeId-$placeId")
        db.child("kmh@naver.com/$groupId/$placeId").removeValue()
            .addOnSuccessListener { Timber.i("Success place delete") }
            .addOnFailureListener { Timber.e("Fail place delete : $it") }
    }

    override fun getUser(id: String): LiveData<User> {
        Timber.d("API Launched")
        val user = MutableLiveData<User>()
        db.child(id).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val userDto = snapshot.getValue(UserDto::class.java)
                    val taste = snapshot.child("Taste").getValue(TasteDto::class.java)
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

    override fun checkNickname(nickname: String): Boolean {
        return true
    }

    override fun insertUser(user: User) {
        db.push().setValue(user)
        // 씨발 왜 안들어가  ㅡㅡ
    }
}