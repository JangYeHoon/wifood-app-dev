package com.example.wifood.data.remote

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.WifoodApp
import com.example.wifood.data.remote.dto.GroupDto
import com.example.wifood.data.remote.dto.PlaceDto
import com.example.wifood.data.remote.dto.TasteDto
import com.example.wifood.data.remote.dto.UserDto
import com.example.wifood.domain.model.Group
import com.example.wifood.domain.model.Place
import com.example.wifood.domain.model.User
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
import timber.log.Timber
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class WifoodApiImpl @Inject constructor(
    private val db: DatabaseReference
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

    override fun getUserInfo(id: String): LiveData<User> {
        val user = MutableLiveData<User>()
        db.child(id).get().addOnSuccessListener {
            if (it.exists()) {
                val userDto = it.getValue(UserDto::class.java)
                user.postValue(userDto?.toUser())
            }
        }
        return user
    }

    override fun checkNickname(nickname: String): Boolean {
        return true
    }

    override fun insertUser(user: User) {
        db.push().setValue(user)
        // 씨발 왜 안들어가  ㅡㅡ
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
}