package com.example.wifood.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.wifood.databinding.ActivityModifyPasswordBinding

class ModifyPassword : AppCompatActivity() {
    private var mBinding: ActivityModifyPasswordBinding?=null
    private val binding get() = mBinding!!
    var imm : InputMethodManager?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityModifyPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager?

    }

    fun hideKeyboard(v : View){
        if(v != null){
            imm?.hideSoftInputFromWindow(v.windowToken,0)
        }
    }

    override fun onDestroy() {
        mBinding= null
        super.onDestroy()
    }
}