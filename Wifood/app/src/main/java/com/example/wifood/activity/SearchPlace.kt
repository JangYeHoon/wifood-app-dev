package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wifood.adapter.SearchPlaceAdapter
import com.example.wifood.databinding.ActivitySearchPlaceBinding
import com.example.wifood.entity.Search
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapTapi
import com.skt.Tmap.poi_item.TMapPOIItem

class SearchPlace : AppCompatActivity() {
    lateinit var binding : ActivitySearchPlaceBinding
    lateinit var searchPlaceAdapter : SearchPlaceAdapter
    var searchResult = MutableLiveData<MutableList<Search>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchPlaceAdapter = SearchPlaceAdapter(this)
        binding.searchResultRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.searchResultRecyclerView.adapter = searchPlaceAdapter
        binding.searchResultRecyclerView.addItemDecoration(DividerItemDecoration(this, 1))
        searchResult.observe(this) {
            searchPlaceAdapter.setListData(it)
            searchPlaceAdapter.notifyDataSetChanged()
        }

        val tmapTAPI = TMapTapi(this)
        tmapTAPI.setSKTMapAuthentication("l7xx56bf2cddf5f84556bdf35558d72f530a")


        binding.searchButton.setOnClickListener {
            searchPlace(binding.keywordText.text.toString(), 20)
        }

        searchPlaceAdapter.setSearchResultClickListener(object: SearchPlaceAdapter.SearchResultClickListener{
            override fun onClick(view: View, position: Int, item: Search) {
                val intent = Intent().apply {
                    putExtra("searchResult", item)
                }
                setResult(RESULT_OK, intent)
                finish()
            }
        })
    }

    private fun searchPlace(keyword: String, page: Int) {
        val search : MutableList<Search> = mutableListOf()
        val tmapData = TMapData()
        tmapData.findTitlePOI(keyword, TMapData.FindTitlePOIListenerCallback {
            for (i in it) {
                val poiItem:TMapPOIItem = i
                val bizName: String = poiItem.middleBizName.toString() + "," + poiItem.lowerBizName + "," + poiItem.detailBizName
                search.add(Search(poiItem.poiAddress.replace("null", ""),
                    poiItem.poiName.toString(), poiItem.poiPoint.latitude, poiItem.poiPoint.longitude, bizName))
                searchResult.postValue(search)
            }
        })
    }
}