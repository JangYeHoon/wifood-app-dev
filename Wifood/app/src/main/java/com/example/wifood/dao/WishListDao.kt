package com.example.wifood.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.entity.Wish
import com.google.firebase.database.*

class WishListDao(groupId : Int) {
    private var wishListDatabase: DatabaseReference = FirebaseDatabase.getInstance().getReference("WishGroup/$groupId/wishlist")

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
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return wishList
    }

    fun insertWishList(wish: Wish) {
        wishListDatabase.child(wish.id.toString()).setValue(wish)
    }
}