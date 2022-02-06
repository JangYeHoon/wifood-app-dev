package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wifood.R
import kotlinx.android.synthetic.main.activity_joinin_food_info.*

class JoininFoodInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joinin_food_info)


        btnGoToMap.setOnClickListener({
            val intent = Intent(this@JoininFoodInfo,Map::class.java)
            startActivity(intent)
        })
    }
}