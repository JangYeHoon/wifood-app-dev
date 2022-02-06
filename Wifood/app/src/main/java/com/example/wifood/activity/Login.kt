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

class Login : AppCompatActivity() {
    val LOCATION_PERMISSION = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    val LOCATION_PERMISSION_REQUEST = 100
    private val pwdStringLambda = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*?&]).{8,15}.\$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val TAG = "Login Page LOG : "
        val db = Firebase.database;
        val dbRootPath = "kg_test_db";
        val dbRef = db.getReference(dbRootPath);
        var idValidation = false
        var pwdValidation = false

        // check id(email) edit text
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
                btnGoHomeButton.isEnabled = idValidation == true && pwdValidation == true
                Log.d(TAG,"Email Correct : $idValidation")

            }
        })
        // check password edit text
        editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?){
            }
            override fun beforeTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){
            }
            override fun onTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){
                pwdValidation = Pattern.matches(pwdStringLambda,editTextPassword.text)
                btnGoHomeButton.isEnabled = idValidation == true && pwdValidation == true
                //Log.d(TAG,"Password Correct : $pwdValidation")
            }
        })

        // loginbutton
        btnGoHomeButton.setOnClickListener{
            dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapShot: DataSnapshot){
                    val builder = AlertDialog.Builder(this@Login)
                    val idText = editTextId.text.toString().replace('.','_') // change to firebase string
                    val pwdText = editTextPassword.text.toString().replace('.','_')// change to firebase string
                    var accountTrue = false;

                    builder.setTitle("로그인")
                    if (snapShot.hasChild(idText)){
                        Log.d(TAG, "Success LOGIN")
                        if (snapShot.child(idText).child("info").child("password").value.toString() == pwdText){
                            Log.d(TAG,snapShot.child(idText).child("info").child("password").toString())
                            builder.setMessage("로그인 성공")
                            accountTrue = true
                        }
                        else
                            builder.setMessage("패스워드 실패")
                        builder.show()
                        if (accountTrue){
                            val intent = Intent(this@Login,Map::class.java)
                            startActivity(intent)
                        }
                        // go to home activiy
                    }
                    else{
                        Log.d(TAG, "Fail to LOGIN, There is no ID");
                        Log.d(TAG, idText);
                        builder.setMessage("아이디가 없습니다")
                        builder.show()
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

            // send sms
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

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }
        }

        btnGoJoinIn.setOnClickListener {
            val intent = Intent(this@Login,Joinin::class.java)
            startActivity(intent)
        }

        fun checkPermission(permissions: Array<String>, permissionRequestNumber:Int){
            val permissionResult = ContextCompat.checkSelfPermission(this, permissions[0])
            when(permissionResult){
                PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
// Go Main Function
                }
                PackageManager.PERMISSION_DENIED -> {
                    ActivityCompat.requestPermissions(this, permissions, permissionRequestNumber)
                }
            }
        }

        fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            when(requestCode){
                LOCATION_PERMISSION_REQUEST -> {
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show()
// Go Main Function
                    }else{
                        Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
// Finish() or Show Guidance on the need for permission
                    }
                }

            }
        }

        fun toast(message: String){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

    }
}