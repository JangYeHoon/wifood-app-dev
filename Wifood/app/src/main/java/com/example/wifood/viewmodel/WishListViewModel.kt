package com.example.wifood.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wifood.dto.WishListDto
import com.example.wifood.entity.Wish
import java.lang.Integer.max

class WishListViewModel(groupId : Int): ViewModel() {
    var wishList: LiveData<MutableList<Wish>>
    private val wishListDto: WishListDto = WishListDto(groupId)
    init {
        wishList = wishListDto.getWishList()
    }

    fun getWishListMaxId() : Int {
        val wishlist = wishList.value
        var maxValue = 0
        if (wishlist != null)
            for (w in wishlist)
                maxValue = max(w.id, maxValue)
        return maxValue
    }

    fun insertWishList(wish: Wish) {
        wishListDto.insertWishList(wish)
    }

    class Factory(val groupId : Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return WishListViewModel(groupId) as T
        }
    }
}