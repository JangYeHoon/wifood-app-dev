package com.example.wifood.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.wifood.dto.WishListDto
import com.example.wifood.entity.Wish

class WishListViewModel: ViewModel() {
    var wishList: LiveData<MutableList<Wish>>
    private val wishListDto: WishListDto = WishListDto()
    init {
        wishList = wishListDto.getWishList()
    }
}