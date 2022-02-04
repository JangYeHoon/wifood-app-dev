package com.example.wifood.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wifood.dto.WishListDto
import com.example.wifood.entity.Wish

class WishListViewModel(groupId : Int): ViewModel() {
    var wishList: LiveData<MutableList<Wish>>
    private val wishListDto: WishListDto = WishListDto(groupId)
    init {
        wishList = wishListDto.getWishList()
    }

    class Factory(val groupId : Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return WishListViewModel(groupId) as T
        }
    }
}