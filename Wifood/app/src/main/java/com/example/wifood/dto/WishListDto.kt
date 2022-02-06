package com.example.wifood.dto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.dao.WishListDao
import com.example.wifood.entity.Wish

class WishListDto(groupId : Int) {
    private val wishListDao = WishListDao(groupId)

    fun getWishList() : LiveData<MutableList<Wish>> {
        val mutableWishList = MutableLiveData<MutableList<Wish>>()
        wishListDao.getWishList().observeForever {
            mutableWishList.value = it
        }
        return mutableWishList
    }

    fun wishListInsert(wish: Wish) {
        wishListDao.wishListInsert(wish)
    }
}