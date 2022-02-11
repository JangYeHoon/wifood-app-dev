package com.example.wifood.dto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wifood.dao.WishListDao
import com.example.wifood.entity.Wish

class WishListDto(groupId : Int) {
    private val wishListDao = WishListDao(groupId)

    // 디비에서 받아온 정보를 이용해 wishlist 업데이트
    fun getWishList() : LiveData<MutableList<Wish>> {
        val mutableWishList = MutableLiveData<MutableList<Wish>>()
        wishListDao.getWishList().observeForever {
            mutableWishList.value = it
        }
        return mutableWishList
    }

    // 디비에 저장하기 위해 dao에 wish를 넘겨줌
    fun insertWishList(wish: Wish) {
        wishListDao.insertWishList(wish)
    }

    // 디비에서 삭제할 id 정보를 dao에 넘겨줌
    fun deleteWishList(wishIdList: ArrayList<Int>) {
        for (i in wishIdList)
            wishListDao.deleteWishList(i)
    }
}