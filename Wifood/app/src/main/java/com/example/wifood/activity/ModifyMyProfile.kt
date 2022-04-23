package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.wifood.databinding.ActivityModifyMyProfileBinding
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

class ModifyMyProfile : AppCompatActivity() {
    private var mBinding: ActivityModifyMyProfileBinding? = null
    private val binding get() = mBinding!!
    var imm : InputMethodManager ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityModifyMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager?


        // back button
        binding.buttonBack.setOnClickListener{
            val intent = Intent(this@ModifyMyProfile,MyPage::class.java)
            intent.putExtra("UserEmail","testingEmail")
            startActivity(intent)
        }

        // when click image
        binding.iamgeProfile.setOnClickListener{
            Toast.makeText(this@ModifyMyProfile, "미구현", Toast.LENGTH_SHORT).show()
        }

        // select image button
        binding.buttonSelectIamge.setOnClickListener{
            Toast.makeText(this@ModifyMyProfile, "미구현", Toast.LENGTH_SHORT).show()
        }
    }


    fun hideKeyboard(v : View){
        if(v != null){
            imm?.hideSoftInputFromWindow(v.windowToken,0)
        }
    }
    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}