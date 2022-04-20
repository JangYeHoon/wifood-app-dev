package com.example.wifood.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wifood.databinding.ActivityModifyMyProfileBinding
import com.example.wifood.databinding.ActivityModifyProfileBinding

class ModifyMyProfile : AppCompatActivity() {
    private var mBinding: ActivityModifyMyProfileBinding? = null
    private val binding get() = mBinding!!

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityModifyMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}