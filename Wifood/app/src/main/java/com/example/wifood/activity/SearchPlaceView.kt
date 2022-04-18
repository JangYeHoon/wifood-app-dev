package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wifood.adapter.SearchPlaceAdapter
import com.example.wifood.databinding.ActivitySearchPlaceBinding
import com.example.wifood.entity.Search
import com.example.wifood.viewmodel.GroupListViewModel
import com.example.wifood.viewmodel.SearchPlaceViewModel
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapTapi
import com.skt.Tmap.poi_item.TMapPOIItem

class SearchPlaceView : AppCompatActivity() {
    lateinit var binding: ActivitySearchPlaceBinding
    lateinit var searchPlaceAdapter: SearchPlaceAdapter
    var searchResult = MutableLiveData<MutableList<Search>>()
    lateinit var inputMethodManager: InputMethodManager
    lateinit var searchPlaceViewModel: SearchPlaceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // tmap api에서 받은 검색 결과를 출력하기 위한 recyclerView 설정
        searchPlaceAdapter = SearchPlaceAdapter(this)
        binding.recyclerViewSearchResult.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewSearchResult.adapter = searchPlaceAdapter
        binding.recyclerViewSearchResult.addItemDecoration(DividerItemDecoration(this, 1))
        searchResult.observe(this) {
            searchPlaceAdapter.setListData(it)
            searchPlaceAdapter.notifyDataSetChanged()
        }

        searchPlaceViewModel = ViewModelProvider(
            this,
            SearchPlaceViewModel.Factory(this)
        ).get(SearchPlaceViewModel::class.java)

        searchPlaceViewModel.searchResult.observe(this) {
            searchPlaceAdapter.setListData(it)
            searchPlaceAdapter.notifyDataSetChanged()
        }

        // 검색 버튼
        binding.imageButtonSearch.setOnClickListener {
            setKeyBoardHide()
            searchPlaceViewModel.setSearchResultByString(binding.editTextKeywordText.text.toString())
        }

        // 엔터키 버튼 입력
        binding.editTextKeywordText.setOnEditorActionListener { _, i, _ ->
            var handled = false
            if (i == EditorInfo.IME_ACTION_DONE) {
                setKeyBoardHide()
                searchPlaceViewModel.setSearchResultByString(binding.editTextKeywordText.text.toString())
                handled = true
            }
            handled
        }

        searchPlaceAdapter.setSearchResultClickListener(object :
            SearchPlaceAdapter.SearchResultClickListener {
            override fun onClick(view: View, position: Int, item: Search) {
                val intent = Intent().apply {
                    putExtra("searchResult", item)
                }
                setResult(RESULT_OK, intent)
                finish()
            }
        })
    }

    private fun setKeyBoardHide() {
        inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.editTextKeywordText.windowToken, 0)
    }
}