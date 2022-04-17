package com.example.wifood.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wifood.R
import kotlinx.android.synthetic.main.activity_coarse_address_daum_api.*
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.JavascriptInterface;
import android.content.Intent;
import android.os.Handler
import android.view.View
import android.webkit.WebChromeClient
import android.widget.TextView


class SearchAddress : AppCompatActivity() {
    private var webView: WebView? = null
    private var txt_address: TextView? = null
    private var handler: Handler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coarse_address_daum_api)

    }
}