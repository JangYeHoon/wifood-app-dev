package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.wifood.R
import kotlinx.android.synthetic.main.activity_joinin_food_info.*

class JoininFoodFavoriteInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joinin_food_info)


        btnGoToMap.setOnClickListener({
            val intent = Intent(this@JoininFoodFavoriteInfo,Map::class.java)
            startActivity(intent)
        })
    }

    override fun onBackPressed() {
        val intent = Intent(this@JoininFoodFavoriteInfo, Login::class.java)
        startActivity(intent)
    }
}