package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.wifood.databinding.ActivityFindIdOrPwdBinding
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

class FindIdOrPwd : AppCompatActivity() {
    private var mBinding: ActivityFindIdOrPwdBinding?=null
    private val binding get() = mBinding!!
    var imm : InputMethodManager?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFindIdOrPwdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // add keybord event
        imm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager?

        // Firebase
        val db = Firebase.database;
        val dbRootPath = "kg_test_db";
        val dbRef = db.getReference(dbRootPath);

        // tab layout event
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?){}
            override fun onTabUnselected(tab: TabLayout.Tab?){}
            override fun onTabSelected(tab: TabLayout.Tab?){
                when(tab!!.position){
                    0-> {
                        binding.frameLayoutFindId.setVisibility(View.VISIBLE)
                        binding.frameLayoutFindPwd.setVisibility(View.INVISIBLE)
                    }

                    1->{
                        binding.frameLayoutFindId.setVisibility(View.INVISIBLE)
                        binding.frameLayoutFindPwd.setVisibility(View.VISIBLE)
                    }
                }
            }
        })

        // back button
        binding.imageButtonBack.setOnClickListener{
            val intent = Intent(this@FindIdOrPwd, Login::class.java)
            startActivity(intent)
        }

        // find id button
        binding.findIdTabButtonFindId.setOnClickListener{
            Toast.makeText(this@FindIdOrPwd, "미구현",Toast.LENGTH_SHORT).show()
        }
        // find id auth
        binding.findIdTabButtonPhoneCheck.setOnClickListener{
            Toast.makeText(this@FindIdOrPwd, "미구현",Toast.LENGTH_SHORT).show()
        }
        // find pwd button
        binding.findPwdTabButtonFindPwd.setOnClickListener{
            Toast.makeText(this@FindIdOrPwd, "미구현",Toast.LENGTH_SHORT).show()
        }

        // find pwd auth
        binding.findPwdTabButtonPhoneCheck.setOnClickListener{
            Toast.makeText(this@FindIdOrPwd, "미구현",Toast.LENGTH_SHORT).show()
        }




    }

    override fun onDestroy() {
        mBinding= null
        super.onDestroy()
    }

    fun hideKeyboard(v : View){
        if(v != null){
            imm?.hideSoftInputFromWindow(v.windowToken,0)
        }
    }
}
