package com.example.wifood.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wifood.databinding.ActivityFindIdOrPwdBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FindIdOrPwd : AppCompatActivity() {
    private var mBinding: ActivityFindIdOrPwdBinding?=null
    private val binding get() = mBinding!!

    override fun onDestroy() {
        mBinding= null
        super.onDestroy()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFindIdOrPwdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase
        val db = Firebase.database;
        val dbRootPath = "kg_test_db";
        val dbRef = db.getReference(dbRootPath);
    }
}