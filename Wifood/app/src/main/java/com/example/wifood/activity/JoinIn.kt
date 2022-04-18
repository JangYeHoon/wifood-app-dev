package com.example.wifood.activity


import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.wifood.R
import com.example.wifood.databinding.ActivityJoininBinding
import com.example.wifood.databinding.ActivityLoginBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_joinin.*
import java.util.regex.Pattern

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

    val TAG = "JoinInUserInfoActivity : "
    private val pwdStringLambda = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*?&]).{8,15}.\$"
    private val nicknameStringLambda = "^[a-zA-Z0-9가-힣]*\$"
    private val fineAddressStringLambda = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$-])"

    private var mBinding: ActivityJoininBinding?=null
    private val binding get() = mBinding!!

    override fun onDestroy() {
        mBinding= null
        super.onDestroy()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityJoininBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // firebase variables
        val db = Firebase.database;
        val dbRootPath = "kg_test_db";
        val dbRef = db.getReference(dbRootPath);

        // email check
        binding.textViewId.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?){}
            override fun beforeTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){}
            override fun onTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(binding.textViewId.text.toString()).matches())
                {
                    binding.textViewIdError.setEnabled(false)
                    emailValid = true
                }
                else{
                    binding.textViewIdError.setEnabled(true)
                    emailValid = true
                }
                checkValid()
            }
        })
        // password check
        binding.textViewPwd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?){
            }
            override fun beforeTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){}
            override fun onTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){
                passwordValid =  Pattern.matches(pwdStringLambda,binding.textViewPwd.text)
                checkValid()
            }
        })
        // password check again
        binding.textViewPwdCheck.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?){}
            override fun beforeTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){}
            override fun onTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){
                passwordValidCheck = Pattern.matches(pwdStringLambda,binding.textViewPwdCheck.text)
                checkValid()
            }
        })
        // nick name check
        binding.textViewNickName.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?){}
            override fun beforeTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){}
            override fun onTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){
                if (Pattern.matches(nicknameStringLambda,binding.textViewNickName.text.toString())){
                    binding.textViewNickNameError.setEnabled(false)
                    nickNameValid = true
                }
                else{
                    binding.textViewNickNameError.setEnabled(true)
                    nickNameValid = false
                }
                checkValid()
            }
        })
        // phone number check
        binding.textViewPhoneCheck.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?){}
            override fun beforeTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){}
            override fun onTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){
                if (android.util.Patterns.PHONE.matcher(binding.textViewPhoneCheck.text.toString()).matches() && binding.textViewPhoneCheck.text.toString().length == 11){
                    binding.textViewPhoneCheckError.setEnabled(false)
                    phoneNumberValid = true
                }
                else{
                    binding.textViewPhoneCheckError.setEnabled(true)
                    phoneNumberValid = false
                }
                checkValid()
            }

        })
        // address check
        //TODO("Coarse Address input")

        //TODO("Fine Address input")
        binding.textViewAddress.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?){}
            override fun beforeTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){}
            override fun onTextChanged(s: CharSequence?, start:Int, before:Int, count : Int){
                //TODO("should convert to fineAddreesLambda")
                if(Pattern.matches(nicknameStringLambda,binding.textViewAddress.text.toString())){
                    binding.textViewAddressError.setEnabled(false)
                    fineAddressValid=true
                }else{
                    binding.textViewAddressError.setEnabled(true)
                    fineAddressValid=false
                }
                checkValid()
            }
        })
        // gender check
        binding.radioGroupGender.setOnCheckedChangeListener{group,checkedId->
            if (binding.radioButtonGenderMale.isChecked)
                genderValue = "Male"
            else if (binding.radioButtonGenderFemale.isChecked)
                genderValue = "Female"
            genderValid = true
            checkValid()
        }

        binding.buttonJoinin.setOnClickListener {
            // Check if error exists
            if (binding.textViewId.text.toString().length == 0){
                binding.textViewIdError.setEnabled(true)
                Toast.makeText(this@Joinin, "이메일 입력 없음", Toast.LENGTH_SHORT).show()
            }
            else if (!emailValid){
                binding.textViewIdError.setEnabled(true)
                Toast.makeText(this@Joinin, "이메일 형식 오류", Toast.LENGTH_SHORT).show()
            }
            else if (binding.textViewPwd.text.toString().length == 0){
                binding.textViewPwderror.setEnabled(true)
                Toast.makeText(this@Joinin, "비밀번호 입력 없음", Toast.LENGTH_SHORT).show()
            }
            else if (!passwordValid){

                Toast.makeText(this@Joinin, "비밀번호 형식 오류", Toast.LENGTH_SHORT).show()
            }
            else if (binding.textViewPwdCheck.text.toString().length == 0){

                Toast.makeText(this@Joinin, "비밀번호 확인 입력 없음", Toast.LENGTH_SHORT).show()
            }
            else if (!passwordValidCheck){
                binding.textViewPwdCheckError.setEnabled(true)
                Toast.makeText(this@Joinin, "비밀번호 확인 형식 오류", Toast.LENGTH_SHORT).show()
            }
            else if (binding.textViewPwd.text.equals(binding.textViewPwdCheck.text))
                Toast.makeText(this@Joinin, "비밀번호 확인 일치 오류", Toast.LENGTH_SHORT).show()

            // 이메일 중복
            // 비밀번호 재입력 체크
            // 닉네임 없음
            // 닉네임 형식 오류
            // 닉네임 중복 체크
            // 핸드폰번호 없음
            // 핸드폰번호 형식 오류 ( 넘어가거나 - 있으면)
            // 핸드폰번호 중복 오류
            // coarse 주소 입력
            // Fine 주소 없음
            // Fine 주소 형식 오류
            // 성별 체크 오류


            // already is email exists check
            val idText = binding.textViewId.text.toString().replace('.', '_') // change to firebase string
            dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapShot: DataSnapshot) {
                    val builder = AlertDialog.Builder(this@Joinin)
                    builder.setTitle("아이디 중복 체크")
                    if (snapShot.hasChild(idText)) {
                        builder.setMessage("아이디 존재")
                        emailValid = false
                        binding.textViewId.setText("")
                        builder.show()
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })

            // check password is same
            if (binding.textViewPwd.text.equals(binding.textViewPwdCheck.text)){
                val builder = AlertDialog.Builder(this@Joinin)
                builder.setTitle("비밀번호 재확인 요청")
                builder.setMessage("비밀번호가 같지 않습니다")
                binding.textViewPwdCheck.setText("")
                passwordValidCheck = false
                builder.show()
            }

            // nickname check
            dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapShot: DataSnapshot) {
                    val nicknameString = binding.textViewNickName.text.toString()
                    var isNicknameExists = false
                    for (user in snapShot.children)
                        isNicknameExists = nicknameString.equals(user.child("name").value)

                    val builder = AlertDialog.Builder(this@Joinin)
                    builder.setTitle("닉네임 중복 체크")
                    if (isNicknameExists){
                        builder.setMessage("닉네임 존재")
                        nickNameValid = false
                        binding.textViewNickName.setText("")
                        builder.show()
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })

            val userEmail = binding.textViewId.text.toString().replace(".com","_com")
            // TODO("should password security")
            // go food info after check
            if(checkValid()){
                val userInfoObject = UserInfo(
                    //TODO("add correct code for user home lat lon using api")
                    password = binding.textViewPwdCheck.text.toString(),
                    nickname = binding.textViewNickName.text.toString(),
                    phoneNumber = binding.textViewPhoneCheck.text.toString(),
                    coarseAddress = "노량진 제 1동 만양로 39",
                    fineAddress = "B01호",
                    gender = genderValue,
                    homeLatitude = 37.50786489934014,
                    homeLongitude = 126.94754349105091
                )
                dbRef.child(userEmail).child("info").setValue(userInfoObject)

                val intent = Intent(this@Joinin,Map::class.java)
                intent.putExtra("UserEmail",userEmail)
                startActivity(intent)
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