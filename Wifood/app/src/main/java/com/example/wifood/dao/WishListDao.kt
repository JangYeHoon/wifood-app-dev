package com.example.wifood.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.entity.Wish
import com.google.firebase.database.*

class WishListDao(groupId : Int) {
    private var wishListDatabase: DatabaseReference = FirebaseDatabase.getInstance().getReference("WishGroup/$groupId/wishlist")

    // 디비에서 wishlist 정보 받아옴
    fun getWishList() : LiveData<MutableList<Wish>> {
        val wishList = MutableLiveData<MutableList<Wish>>()
        wishListDatabase.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dbList : MutableList<Wish> = mutableListOf()
                if (snapshot.exists()) {
                    for (wishListSnapshot in snapshot.children) {
                        val wish = wishListSnapshot.getValue(Wish::class.java)
                        dbList.add(wish!!)
                        wishList.value = dbList
                    }
                } else wishList.postValue(null)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return wishList
    }

    // 디비에 해당 wish 정보 추가 or 수정
    fun insertWishList(wish: Wish) {
        wishListDatabase.child(wish.id.toString()).setValue(wish)
    }

    // 디비에서 해당 id를 가진 food 삭제
    fun deleteWishList(wishId : Int) {
        wishListDatabase.child(wishId.toString()).removeValue()
    }
}