package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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

        binding.buttonBack.setOnClickListener{
            val intent = Intent(this@ModifyPassword,ModifyMyInfo::class.java)
            intent.putExtra("UserEmail","testingEmail")
            startActivity(intent)
        }

        binding.buttonModifyPassword.setOnClickListener {
            Toast.makeText(this@ModifyPassword, "미구현", Toast.LENGTH_SHORT).show()
        }


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