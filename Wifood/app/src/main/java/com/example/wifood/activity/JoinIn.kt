package com.example.wifood.activity


import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.wifood.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_joinin.*
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Pattern
import kotlin.collections.Map

//TODO("본인 확인 구현 필요")
class Joinin : AppCompatActivity() {
    var emailValid = false
    var passwordValid = false
    var passwordValidCheck = false
    var genderValid = true
    var coarseAddressValid = true
    var fineAddressValid = true
    var phoneNumberValid = false
    var nickNameValid = false
    var genderValue = ""
    var userHomeLatitude = 37.50786489934014
    var userHomeLongitude = 126.94754349105091

    private val pwdStringLambda = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*?&]).{8,15}.\$"
    private val nicknameStringLambda = "^[a-zA-Z0-9가-힣]*\$"
    private val fineAddressStringLambda = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$-])"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joinin)

        // firebase variables
        val db = Firebase.database;
        val dbRootPath = "kg_test_db";
        val dbRef = db.getReference(dbRootPath);

        val modifyUserEmail = intent.getStringExtra("UserEmail").toString()

        if (modifyUserEmail  != "null"){

            dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapShot: DataSnapshot) {
                    // put user email = false
                    editTextJoinEmail.setText(modifyUserEmail.replace("_com",".com"))
                    editTextJoinNickName.setText(snapShot.child(modifyUserEmail).child("info").child("nickname").value.toString())
                    editTextJoinPhone.setText(snapShot.child(modifyUserEmail).child("info").child("phoneNumber").value.toString())
                    //editTextJoinCoarseAddress.setText(snapShot.child(modifyUserEmail).child("info").child("coarseAddress").getValue().toString())
                    editTextJoinCoarseAddress.setText("아직 구현이 안됌 ㅠㅠ")
                    editTextJoinFineAddress.setText(snapShot.child(modifyUserEmail).child("info").child("fineAddress").value.toString())
                    userHomeLatitude = snapShot.child(modifyUserEmail).child("info").child("homeLatitude").value.toString().toDouble()
                    userHomeLongitude = snapShot.child(modifyUserEmail).child("info").child("homeLongitude").value.toString().toDouble()
                    if (snapShot.child(modifyUserEmail).child("info").child("gender").value.toString() == "Male"){
                        radioButtonJoinMale.isChecked = true
                        radioButtonJoinFemale.isChecked = false
                    }
                    else{
                        radioButtonJoinMale.isChecked = false
                        radioButtonJoinFemale.isChecked = true
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
            // set editText not clickable
            editTextJoinEmail.setBackgroundColor(Color.LTGRAY)
            editTextJoinEmail.isClickable = false
            editTextJoinNickName.setBackgroundColor(Color.LTGRAY)
            editTextJoinNickName.isClickable = false
            radioButtonJoinMale.isClickable = false
            radioButtonJoinFemale.isClickable = false
            editTextJoinPassword.text.clear()
            editTextJoinPasswordCheck.text.clear()
        }

        // email check
        editTextJoinEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?){}
            override fun beforeTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){}
            override fun onTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(editTextJoinEmail.text.toString()).matches())
                {
                    editTextJoinEmail.setTextColor(Color.BLACK)
                    emailValid = true
                }
                else{
                    editTextJoinEmail.setTextColor(Color.RED)
                    emailValid = true
                }
                checkValid()
            }
        })
        // password check
        editTextJoinPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?){
            }
            override fun beforeTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){}
            override fun onTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){
                passwordValid =  Pattern.matches(pwdStringLambda,editTextJoinPassword.text)
                checkValid()
            }
        })
        // password check again
        editTextJoinPasswordCheck.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?){}
            override fun beforeTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){}
            override fun onTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){
                passwordValidCheck = Pattern.matches(pwdStringLambda,editTextJoinPasswordCheck.text)
                checkValid()
            }
        })
        // nick name check
        editTextJoinNickName.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?){}
            override fun beforeTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){}
            override fun onTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){
                if (Pattern.matches(nicknameStringLambda,editTextJoinNickName.text.toString())){
                    editTextJoinNickName.setTextColor(Color.BLACK)
                    nickNameValid = true
                }
                else{
                    editTextJoinNickName.setTextColor(Color.RED)
                    nickNameValid = false
                }
                checkValid()
            }
        })
        // phone number check
        editTextJoinPhone.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?){}
            override fun beforeTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){}
            override fun onTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){
                if (android.util.Patterns.PHONE.matcher(editTextJoinPhone.text.toString()).matches() && editTextJoinPhone.text.toString().length == 11){
                    editTextJoinPhone.setTextColor(Color.BLACK)
                    phoneNumberValid = true
                }
                else{
                    editTextJoinPhone.setTextColor(Color.RED)
                    phoneNumberValid = false
                }
                checkValid()
            }

        })
        // address check ( set latitude and longitude
        //TODO("Coarse Address input")
        editTextJoinCoarseAddress.setText("아직 구현이 안됌 ㅠㅠ")

        //TODO("Fine Address input")
        editTextJoinFineAddress.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?){}
            override fun beforeTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){}
            override fun onTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){
                //TODO("should convert to fineAddreesLambda")
                if(Pattern.matches(nicknameStringLambda,editTextJoinFineAddress.text.toString())){
                    editTextJoinFineAddress.setTextColor(Color.BLACK)
                    fineAddressValid=true
                }else{
                    editTextJoinFineAddress.setTextColor(Color.RED)
                    fineAddressValid=false
                }
                checkValid()
            }
        })

        // gender check
        radioGroupJoinGender.setOnCheckedChangeListener{group,checkedId->
            if (radioButtonJoinMale.isChecked)
                genderValue = "Male"
            else if (radioButtonJoinFemale.isChecked)
                genderValue = "Female"
            genderValid = true
            checkValid()
        }

        btnGoJoinInFoodInfo.setOnClickListener {
            // Check if error exists
            if (editTextJoinEmail.text.isEmpty())
                Toast.makeText(this@Joinin, "이메일 입력 없음", Toast.LENGTH_SHORT).show()
            else if (!emailValid){
                Toast.makeText(this@Joinin, "이메일 형식 오류", Toast.LENGTH_SHORT).show()
                editTextJoinPassword.text.clear()
            }
            else if (editTextJoinPassword.text.isEmpty())
                Toast.makeText(this@Joinin, "비밀번호 입력 없음", Toast.LENGTH_SHORT).show()
            else if (!passwordValid){
                Toast.makeText(this@Joinin, "비밀번호 형식 오류", Toast.LENGTH_SHORT).show()
                editTextJoinPassword.text.clear()
            }
            else if (editTextJoinPasswordCheck.text.isEmpty())
                Toast.makeText(this@Joinin, "비밀번호 확인 입력 없음", Toast.LENGTH_SHORT).show()
            else if (!passwordValidCheck){
                Toast.makeText(this@Joinin, "비밀번호 확인 형식 오류", Toast.LENGTH_SHORT).show()
                editTextJoinPasswordCheck.text.clear()
            }
            else if (editTextJoinPassword.text.equals(editTextJoinPasswordCheck.text)){
                Toast.makeText(this@Joinin, "비밀번호 일치 안함", Toast.LENGTH_SHORT).show()
                editTextJoinPassword.text.clear()
                editTextJoinPasswordCheck.text.clear()
            }
            else if (editTextJoinNickName.text.isEmpty())
                Toast.makeText(this@Joinin, "닉네임 입력 없음", Toast.LENGTH_SHORT).show()
            else if (!nickNameValid){
                Toast.makeText(this@Joinin, "닉네임 입력 형식 오류", Toast.LENGTH_SHORT).show()
                editTextJoinNickName.text.clear()
            }
            else if (editTextJoinPhone.text.isEmpty())
                Toast.makeText(this@Joinin, "핸드폰 입력 없음", Toast.LENGTH_SHORT).show()
            else if (!phoneNumberValid){
                Toast.makeText(this@Joinin, "핸드폰 입력 형식 오류", Toast.LENGTH_SHORT).show()
                editTextJoinPhone.text.clear()
            }
            else if (!coarseAddressValid)
                Toast.makeText(this@Joinin, "주소 입력 없음", Toast.LENGTH_SHORT).show()
            else if (editTextJoinFineAddress.text.isEmpty())
                Toast.makeText(this@Joinin, "상세 주소 입력 없음", Toast.LENGTH_SHORT).show()
            else if (!fineAddressValid){
                Toast.makeText(this@Joinin, "상세 주소 입력 형식 오류", Toast.LENGTH_SHORT).show()
                editTextJoinFineAddress.text.clear()
            }
            else if (!genderValid)
                Toast.makeText(this@Joinin, "성별 입력 없음", Toast.LENGTH_SHORT).show()
            else {
                // already is email exists check
                val userEmail = editTextJoinEmail.text.toString().replace(".com","_com")
                dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapShot: DataSnapshot) {
                        // check email is exists
                        if (snapShot.hasChild(userEmail)) {
                            emailValid = false
                            editTextJoinEmail.text.clear()
                            Toast.makeText(this@Joinin, "이메일 존재", Toast.LENGTH_SHORT).show()
                        }
                        // check nickname and phone number is exists
                        val nicknameString = editTextJoinNickName.text.toString()
                        val phoneString = editTextJoinPhone.text.toString()
                        var isNicknameExists = false
                        var isPhoneExists = false
                        for (user in snapShot.children){
                            isNicknameExists = nicknameString.equals(user.child("name").value)
                            isPhoneExists = phoneString.equals(user.child("phoneNumber").value)
                        }
                        if (isNicknameExists)
                            Toast.makeText(this@Joinin, "닉네임 존재", Toast.LENGTH_SHORT).show()
                        if (isPhoneExists)
                            Toast.makeText(this@Joinin, "핸드폰 번호 존재", Toast.LENGTH_SHORT).show()

                    }
                    override fun onCancelled(error: DatabaseError) {}
                })

                // TODO("should password security")
                // go food info after check
                if(checkValid()){
                    val userInfoObject = UserInfo(
                        //TODO("add correct code for user home lat lon using api")
                        password = editTextJoinPasswordCheck.text.toString(),
                        nickname = editTextJoinNickName.text.toString(),
                        phoneNumber = editTextJoinPhone.text.toString(),
                        coarseAddress = "노량진 제 1동 만양로 39",
                        fineAddress = "B01호",
                        gender = genderValue,
                        homeLatitude = userHomeLatitude,
                        homeLongitude = userHomeLongitude
                    )
                    dbRef.child(userEmail).child("info").setValue(userInfoObject)

                    if (modifyUserEmail  != "null"){ // if modify case
                        val intent = Intent(this@Joinin, Map::class.java)
                        intent.putExtra("UserEmail",userEmail)
                        startActivity(intent)
                    }
                    else { // if new user case
                        val intent = Intent(this@Joinin,JoininFoodFavoriteInfo::class.java)
                        intent.putExtra("UserEmail",userEmail)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    fun checkValid() : Boolean{
        return emailValid && passwordValid && passwordValidCheck && genderValid && coarseAddressValid && fineAddressValid && phoneNumberValid && nickNameValid
    }

}

data class UserInfo(
    val password : String?=null,
    val nickname : String?=null,
    val phoneNumber : String?=null,
    val coarseAddress : String?=null,
    val fineAddress : String?=null,
    val gender : String?=null,
    val homeLatitude : Double?=null,
    val homeLongitude : Double?=null,
)