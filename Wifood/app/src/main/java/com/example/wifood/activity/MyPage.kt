package com.example.wifood.activity

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_my_page.*;
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.wifood.R
import com.example.wifood.databinding.ActivityFindIdOrPwdBinding
import com.example.wifood.databinding.ActivityMypagehomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_map.*

class MyPage : AppCompatActivity() {

    private var mBinding: ActivityMypagehomeBinding?=null
    private val binding get() = mBinding!!

    override fun onDestroy() {
        mBinding= null
        super.onDestroy()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMypagehomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonModifyProfile.setOnClickListener {
            val intent = Intent(this, ModifyMyProfile::class.java)
            intent.putExtra("UserEmail", "testingEmail")
            startActivity(intent)
        }

        binding.buttonGoList.setOnClickListener {
            val intent = Intent(this, GroupListView::class.java)
            intent.putExtra("UserEmail", "testingEmail")
            startActivity(intent)
        }

        binding.buttonGoMap.setOnClickListener {
            val intent = Intent(this, Map::class.java)
            intent.putExtra("UserEmail", "testingEmail")
            startActivity(intent)
        }

        binding.buttonLogout.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

    }

    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        // Firebase
        val db = Firebase.database;
        val dbRootPath = "kg_test_db";
        val dbRef = db.getReference(dbRootPath);

        val userEmail = intent.getStringExtra("UserEmail").toString()
        // set user nickname
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapShot: DataSnapshot) {
                val nickname = snapShot.child(userEmail).child("info").child("nickname").value.toString()
                textViewMyPageUserName.text = nickname
                textViewMyPageUserEmail.text = userEmail.replace("_com",".com")
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        // button event : modify my info
        btnMyPageModifyMyInfo.setOnClickListener {
            //val intent = Intent(this, Joinin::class.java)
            //intent.putExtra("UserEmail",userEmail)sssss
            // joinin 가서 user email가지고 나머지 정보 채우고 deactivate 해줘야함
                    // 체크도 간소화해서 하자.
            //startActivity(intent)
        }

        // button event : modify my favor
        btnMyPageModifyMyFavor.setOnClickListener {
            //val intent = Intent(this, ModifyMyFavor::class.java)
            //intent.putExtra("UserEmail",userEmail)
            //startActivity(intent)
        }

        //button event : go option

        // image button event : go option

        // button event : logout
        btnMyPageLogout.setOnClickListener{
            val autoLoginPref = getSharedPreferences("wifoodAutoLogin", MODE_PRIVATE);
            val autoLoginEditor = autoLoginPref.edit();
            autoLoginEditor.clear();
            autoLoginEditor.apply();
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        // button event : user delete
        btnMyPageDeleteUser.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder
                .setTitle("회원 탈퇴")
                .setMessage("정말 탈퇴할까요?")
                .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->
                })
                .setPositiveButton("확인", DialogInterface.OnClickListener { dialog,which->
                    // remove auto login pref
                    val autoLoginPref = getSharedPreferences("wifoodAutoLogin", MODE_PRIVATE);
                    val autoLoginEditor = autoLoginPref.edit();
                    autoLoginEditor.clear();
                    autoLoginEditor.apply();

                    // remove user info in database
                    dbRef.child(userEmail).removeValue()

                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
            })
            builder.show()
        }
    }
    */
}