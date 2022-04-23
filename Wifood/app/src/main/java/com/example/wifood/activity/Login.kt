package com.example.wifood.activity

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.util.Log;

// firebase import
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.*
import com.google.firebase.database.*
import com.google.firebase.database.ktx.*

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.wifood.R
import java.util.regex.Pattern
import com.example.wifood.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private val pwdStringLambda = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*?&]).{8,15}.\$"
    val TAG = "Login Page LOG : "

    private var mBinding: ActivityLoginBinding?=null
    private val binding get() = mBinding!!
    var imm : InputMethodManager ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // add keybord event
        imm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager?

        binding.buttonLogin.setOnClickListener {
            val intent = Intent(this@Login, Map::class.java)
            intent.putExtra("UserEmail", "testingEmail")
            startActivity(intent)
        }

        binding.textJoinin.setOnClickListener {
            val intent = Intent(this@Login,Joinin::class.java)
            startActivity(intent)
        }

        // find password button
        binding.textFindIdPwd.setOnClickListener{
            val intent = Intent(this@Login,FindIdOrPwd::class.java)
            startActivity(intent)
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



    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase
        val db = Firebase.database;
        val dbRootPath = "kg_test_db";
        val dbRef = db.getReference(dbRootPath);
        var idValidation = false
        var pwdValidation = false

        // Auto Login variables
        val autoLoginPref : SharedPreferences = this.getSharedPreferences("wifoodAutoLogin", Context.MODE_PRIVATE)
        val autoLoginEditor : SharedPreferences.Editor = autoLoginPref.edit()
        val autoLoginId = autoLoginPref.getString("WifoodLoginId","").toString()

        if (!autoLoginId.isNullOrBlank()){
            Toast.makeText(this@Login, "자동 로그인", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@Login,Map::class.java).putExtra("UserEmail",autoLoginId))
        }


        // login button
         binding.buttonLogin.setOnClickListener {
            pwdValidation = Pattern.matches(pwdStringLambda, binding.textViewPwd.text)
            if (binding.textViewId.text.toString().length == 0)
            // when no id input
                Toast.makeText(this@Login, "아이디 없음", Toast.LENGTH_SHORT).show()
            else if (!idValidation)
            // when id format is wrong
                Toast.makeText(this@Login, "아이디 형식 오류", Toast.LENGTH_SHORT).show()
            else if (binding.textViewPwd.text.toString().length == 0)
                Toast.makeText(this@Login, "비밀번호 없음", Toast.LENGTH_SHORT).show()
            else if (!pwdValidation)
                Toast.makeText(this@Login, "비밀번호 형식 오류", Toast.LENGTH_SHORT).show()
            else {
                dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapShot: DataSnapshot) {
                        val idText = binding.textViewId.text.toString()
                            .replace(".com", "_com") // change to firebase string
                        val pwdText = binding.textViewPwd.text.toString()
                            .replace('.', '_')// change to firebase string
                        var accountTrue = false;

                        if (snapShot.hasChild(idText)) {
                            if (snapShot.child(idText).child("info").child("password").value.toString() == pwdText) {
                                accountTrue = true
                                Toast.makeText(this@Login, "로그인 성공", Toast.LENGTH_SHORT).show()
                            } else
                                Toast.makeText(this@Login, "패스워드 실패", Toast.LENGTH_SHORT).show()
                            if (accountTrue) {
                                // set auto login
                                autoLoginEditor.putString("WifoodLoginId",idText)
                                autoLoginEditor.apply()
                                // if user didnt add food favorite, then go to food favorite page
                                val intent = Intent(this@Login, Map::class.java)
                                intent.putExtra("UserEmail",idText)
                                startActivity(intent)

                            }
                            // go to home activiy
                        } else {
                            Toast.makeText(this@Login, "아이디 없음", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            }
        }



        // joinin button
        binding.textViewJoinin.setOnClickListener {
            val intent = Intent(this@Login,Joinin::class.java)
            startActivity(intent)
        }

        // find password button
        binding.textViewFindIdPwd.setOnClickListener{
            val intent = Intent(this@Login,FindIdOrPwd::class.java)
            startActivity(intent)
        }

        // check id(email) listener
        binding.textViewId.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?){
            }
            override fun beforeTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){
            }
            override fun onTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(binding.textViewId.text.toString()).matches()){
                    idValidation = true
                }
                else{
                    binding.textViewId.setTextColor(Color.RED)
                    idValidation = false
                }
            }
        })

    }*/

    var backKeyPressedTime : Long = 0
    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis()
            Toast.makeText(this@Login, "두 번 누르면 앱이 종료됩니다", Toast.LENGTH_SHORT).show()
            return
        }else{
            finishAffinity()
        }
    }

}