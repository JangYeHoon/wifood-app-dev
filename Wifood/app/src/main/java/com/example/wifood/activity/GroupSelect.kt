package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wifood.databinding.ActivityGroupSelectBinding

class GroupSelect : AppCompatActivity() {
    lateinit var binding : ActivityGroupSelectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.foodGroupBtn.setOnClickListener {
            val intent = Intent(this@GroupSelect, FoodGroup::class.java)
            startActivity(intent)
        }

        binding.wishGroupBtn.setOnClickListener {
            val intent = Intent(this@GroupSelect, WishGroup::class.java)
            startActivity(intent)
        }
    }
}