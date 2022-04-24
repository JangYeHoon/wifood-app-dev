package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.wifood.databinding.ActivitySettingsBinding

class Settings : AppCompatActivity() {
    private var mBinding: ActivitySettingsBinding?=null
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // back button
        binding.buttonBack.setOnClickListener{
            val intent = Intent(this@Settings,MyPage::class.java)
            intent.putExtra("UserEmail","testingEmail")
            startActivity(intent)
        }

        // version info button
        binding.buttonVersionInfo.setOnClickListener{
            Toast.makeText(this@Settings, "미구현", Toast.LENGTH_SHORT).show()
        }

        // feed back button
        binding.buttonFeeback.setOnClickListener{
            Toast.makeText(this@Settings, "미구현", Toast.LENGTH_SHORT).show()
        }

        // developer info button
        binding.buttonDeveloperInfo.setOnClickListener{
            Toast.makeText(this@Settings, "미구현", Toast.LENGTH_SHORT).show()
        }

        // service Authentication info
        binding.buttonServiceAuthentication.setOnClickListener{
            Toast.makeText(this@Settings, "미구현", Toast.LENGTH_SHORT).show()
        }



    }
    override fun onDestroy() {
        mBinding= null
        super.onDestroy()
    }
}