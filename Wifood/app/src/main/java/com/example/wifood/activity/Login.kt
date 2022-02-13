package com.example.wifood.activity

import android.Manifest
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import kotlinx.android.synthetic.main.activity_login.*
import android.util.Log;

// firebase import
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.*
import com.google.firebase.database.*
import com.google.firebase.database.ktx.*

import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.wifood.R
import java.util.regex.Pattern
import kotlin.collections.Map

class Login : AppCompatActivity() {
    private val pwdStringLambda = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*?&]).{8,15}.\$"
    val TAG = "Login Page LOG : "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Firebase
        val db = Firebase.database;
        val dbRootPath = "kg_test_db";
        val dbRef = db.getReference(dbRootPath);
        var idValidation = false
        var pwdValidation = false

        // login button
        btnGoHomeButton.setOnClickListener {
            pwdValidation = Pattern.matches(pwdStringLambda, editTextPassword.text)
            if (editTextId.text.toString().length == 0)
            // when no id input
                Toast.makeText(this@Login, "아이디 없음", Toast.LENGTH_SHORT).show()
            else if (!idValidation)
            // when id format is wrong
                Toast.makeText(this@Login, "아이디 형식 오류", Toast.LENGTH_SHORT).show()
            else if (editTextPassword.text.toString().length == 0)
                Toast.makeText(this@Login, "비밀번호 없음", Toast.LENGTH_SHORT).show()
            else if (!pwdValidation)
                Toast.makeText(this@Login, "비밀번호 형식 오류", Toast.LENGTH_SHORT).show()
            else {
                dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapShot: DataSnapshot) {
                        val idText = editTextId.text.toString()
                            .replace(".com", "_com") // change to firebase string
                        val pwdText = editTextPassword.text.toString()
                            .replace('.', '_')// change to firebase string
                        var accountTrue = false;

                        if (snapShot.hasChild(idText)) {
                            Log.d(TAG, "Success LOGIN")
                            if (snapShot.child(idText).child("info")
                                    .child("password").value.toString() == pwdText
                            ) {
                                Log.d(TAG,
                                    snapShot.child(idText).child("info").child("password")
                                        .toString()
                                )
                                Toast.makeText(this@Login, "로그인 성공", Toast.LENGTH_SHORT).show()
                                accountTrue = true
                            } else
                                Toast.makeText(this@Login, "패스워드 실패", Toast.LENGTH_SHORT).show()
                            if (accountTrue) {
                                // if user didnt add food favorite, then go to food favorite page
                                var intent = Intent()
                                if (snapShot.child(idText).hasChild("Info_Food_Favorite"))
                                    intent = Intent(this@Login, Map::class.java)
                                else
                                    intent = Intent(this@Login, JoininFoodFavoriteInfo::class.java)
                                startActivity(intent)
                            }
                            // go to home activiy
                        } else {
                            Log.d(TAG, "Fail to LOGIN, There is no ID");
                            Toast.makeText(this@Login, "아이디 없음", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            }
        }

        // join in button
        btnGoJoinIn.setOnClickListener {
            val intent = Intent(this@Login,Joinin::class.java)
            startActivity(intent)
        }

        // find password button
        btnFindPwd.setOnClickListener{
            dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapShot: DataSnapshot) {
                    val builder = AlertDialog.Builder(this@Login)
                    val idText = editTextId.text.toString()
                    if(snapShot.hasChild(idText)){
                        val userPhoneNum = snapShot.child(idText).child("info").child("phone_number").value.toString()
                        val userUri = Uri.parse("smsto:${userPhoneNum}")
                        val userIntent = Intent(Intent.ACTION_SENDTO,userUri)
                        val sms_body_text = snapShot.child(idText).child("info").child("password").value.toString()
                        userIntent.putExtra("sms_body", "Wifood User Password : $sms_body_text")
                        builder.setTitle("Sending PWD")
                            .setMessage("Successed , Dont forget your password")
                        startActivity(userIntent)
                    }
                    else {
                        builder.setTitle("Sending PWD")
                            .setMessage("Failed , There is no ID")
                    }
                    builder.show()
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }

        // check id(email) listener
        editTextId.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?){
            }
            override fun beforeTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){
            }
            override fun onTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(editTextId.text.toString()).matches()){
                    editTextId.setTextColor(Color.BLACK)
                    idValidation = true
                }
                else{
                    editTextId.setTextColor(Color.RED)
                    idValidation = false
                }
            }
        })

    }

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