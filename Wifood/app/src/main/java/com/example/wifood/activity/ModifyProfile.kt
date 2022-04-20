package com.example.wifood.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wifood.databinding.ActivityModifyProfileBinding

class ModifyProfile : AppCompatActivity() {

    private var mBinding: ActivityModifyProfileBinding? = null
    private val binding get() = mBinding!!

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityModifyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}