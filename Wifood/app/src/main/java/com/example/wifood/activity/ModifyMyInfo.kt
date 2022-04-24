package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.wifood.databinding.ActivityModifyMyInfoBinding

class ModifyMyInfo : AppCompatActivity() {
    private var mBinding: ActivityModifyMyInfoBinding?=null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityModifyMyInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // back button
        binding.buttonBack.setOnClickListener{
            val intent = Intent(this@ModifyMyInfo,MyPage::class.java)
            intent.putExtra("UserEmail","testingEmail")
            startActivity(intent)
        }

        // modify password
        binding.buttonModifyPassword.setOnClickListener{
            val intent = Intent(this@ModifyMyInfo,ModifyPassword::class.java)
            intent.putExtra("UserEmail","testingEmail")
            startActivity(intent)
        }

        // modify phone number
        binding.buttonModifyPhoneNumber.setOnClickListener{
            Toast.makeText(this@ModifyMyInfo, "미구현", Toast.LENGTH_SHORT).show()
        }

        // modify adress
        binding.buttonModifyAddress.setOnClickListener{
            Toast.makeText(this@ModifyMyInfo, "미구현", Toast.LENGTH_SHORT).show()
        }

        // user withdraw
        binding.buttonWithdraw.setOnClickListener{
            Toast.makeText(this@ModifyMyInfo, "미구현", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroy() {
        mBinding= null
        super.onDestroy()
    }


}