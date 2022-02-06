package com.example.wifood.dto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.dao.WishListDao
import com.example.wifood.entity.Wish

class WishListDto {
    private val wishListDao = WishListDao()

    fun getWishList() : LiveData<MutableList<Wish>> {
        val mutableWishList = MutableLiveData<MutableList<Wish>>()
        wishListDao.getWishList().observeForever {
            mutableWishList.value = it
        }
        return mutableWishList
    }
}