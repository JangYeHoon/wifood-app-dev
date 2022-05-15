package com.example.wifood.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wifood.domain.entity.Search
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapTapi
import com.skt.Tmap.poi_item.TMapPOIItem

class SearchPlaceViewModel(context: Context) : ViewModel() {
    private val tmapTAPI = TMapTapi(context)
    var searchResult = MutableLiveData<MutableList<Search>>()
    var search = Search()

    init {
        tmapTAPI.setSKTMapAuthentication("l7xx56bf2cddf5f84556bdf35558d72f530a")
    }

    fun setSearchResultInstance(search: Search) {
        this.search = search
    }

    fun getSearchName(): String {
        return search.name
    }

    fun getSearchAddress(): String {
        return search.fullAddress
    }

    fun getSearchLatitude(): Double {
        return search.latitude
    }

    fun getSearchLongitude(): Double {
        return search.longitude
    }

    fun setSearchResultByString(keyword: String) {
        val search: MutableList<Search> = mutableListOf()
        val tmapData = TMapData()
        tmapData.findAllPOI(keyword, TMapData.FindAllPOIListenerCallback {
            for (i in it) {
                val poiItem: TMapPOIItem = i
                val bizName: String =
                    poiItem.middleBizName.toString() + "," + poiItem.lowerBizName + "," + poiItem.detailBizName
                var addressRoad = ""
                for (a in poiItem.newAddressList)
                    addressRoad = a.fullAddressRoad
                addressRoad += poiItem.detailAddrName.replace("null", "")
                search.add(
                    Search(
                        addressRoad, poiItem.poiName.toString(), poiItem.poiPoint.latitude,
                        poiItem.poiPoint.longitude, bizName
                    )
                )
                searchResult.postValue(search)
            }
        })
    }

    class Factory(val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SearchPlaceViewModel(context) as T
        }
    }
}