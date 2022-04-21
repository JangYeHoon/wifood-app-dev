package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.wifood.R
import com.example.wifood.databinding.ActivityJoininFoodInfoBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class JoininFoodFavoriteInfo : AppCompatActivity() {
    lateinit var binding: ActivityJoininFoodInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoininFoodInfoBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_joinin_food_info)

        // database setting
        val db = Firebase.database;
        val dbRootPath = "kg_test_db";
        val dbRef = db.getReference(dbRootPath);

        // get intent value
        val userEmail = intent.getStringExtra("UserEmail")

        var userTasteFavoriteInfo = UserTasteFavoriteBaseInfo()

        // put each value of radiogroup to data class
        binding.radioGroupSaltiness.setOnCheckedChangeListener{group,checkedId->
            when(checkedId){
                R.id.radioButtonSaltiness1-> userTasteFavoriteInfo.Saltiness = 1
                R.id.radioButtonSaltiness2-> userTasteFavoriteInfo.Saltiness = 2
                R.id.radioButtonSaltiness3-> userTasteFavoriteInfo.Saltiness = 3
                R.id.radioButtonSaltiness4-> userTasteFavoriteInfo.Saltiness = 4
                R.id.radioButtonSaltiness5-> userTasteFavoriteInfo.Saltiness = 5
            }
        }
        binding.radioGroupSweetness.setOnCheckedChangeListener{group,checkedId->
            when(checkedId){
                R.id.radioButtonSweetness1-> userTasteFavoriteInfo.Sweetness = 1
                R.id.radioButtonSweetness2-> userTasteFavoriteInfo.Sweetness = 2
                R.id.radioButtonSweetness3-> userTasteFavoriteInfo.Sweetness = 3
                R.id.radioButtonSweetness4-> userTasteFavoriteInfo.Sweetness = 4
                R.id.radioButtonSweetness5-> userTasteFavoriteInfo.Sweetness = 5
            }
        }
        binding.radioGroupSourness.setOnCheckedChangeListener{group,checkedId->
            when(checkedId){
                R.id.radioButtonSourness1-> userTasteFavoriteInfo.Sourness = 1
                R.id.radioButtonSourness2-> userTasteFavoriteInfo.Sourness = 2
                R.id.radioButtonSourness3-> userTasteFavoriteInfo.Sourness = 3
                R.id.radioButtonSourness4-> userTasteFavoriteInfo.Sourness = 4
                R.id.radioButtonSourness5-> userTasteFavoriteInfo.Sourness = 5
            }
        }
        binding.radioGroupSavory.setOnCheckedChangeListener{group,checkedId->
            when(checkedId){
                R.id.radioButtonSavory1-> userTasteFavoriteInfo.Savory = 1
                R.id.radioButtonSavory2-> userTasteFavoriteInfo.Savory = 2
                R.id.radioButtonSavory3-> userTasteFavoriteInfo.Savory = 3
                R.id.radioButtonSavory4-> userTasteFavoriteInfo.Savory = 4
                R.id.radioButtonSavory5-> userTasteFavoriteInfo.Savory = 5
            }
        }
        binding.radioGroupSpiciness.setOnCheckedChangeListener{group,checkedId->
            when(checkedId){
                R.id.radioButtonSpiciness1-> userTasteFavoriteInfo.Spiciness = 1
                R.id.radioButtonSpiciness2-> userTasteFavoriteInfo.Spiciness = 2
                R.id.radioButtonSpiciness3-> userTasteFavoriteInfo.Spiciness = 3
                R.id.radioButtonSpiciness4-> userTasteFavoriteInfo.Spiciness = 4
                R.id.radioButtonSpiciness5-> userTasteFavoriteInfo.Spiciness = 5
            }
        }

        binding.btnGoToMap.setOnClickListener({
            // check if there is something null in data class
            if (userTasteFavoriteInfo.Saltiness == 0)
                Toast.makeText(this@JoininFoodFavoriteInfo, "짠맛 선호도를 선택해주세요", Toast.LENGTH_SHORT).show()
            else if (userTasteFavoriteInfo.Sweetness == 0)
                Toast.makeText(this@JoininFoodFavoriteInfo, "단맛 선호도를 선택해주세요", Toast.LENGTH_SHORT).show()
            else if (userTasteFavoriteInfo.Savory == 0)
                Toast.makeText(this@JoininFoodFavoriteInfo, "감칠맛 선호도를 선택해주세요", Toast.LENGTH_SHORT).show()
            else if (userTasteFavoriteInfo.Spiciness == 0)
                Toast.makeText(this@JoininFoodFavoriteInfo, "매운맛 선호도를 선택해주세요", Toast.LENGTH_SHORT).show()
            else if (userTasteFavoriteInfo.Sourness == 0)
                Toast.makeText(this@JoininFoodFavoriteInfo, "신맛 선호도를 선택해주세요", Toast.LENGTH_SHORT).show()
            else {
                // Put data to database
                Toast.makeText(this@JoininFoodFavoriteInfo, "회원 가입 완료", Toast.LENGTH_SHORT).show()

                if (userEmail != null) {
                    dbRef.child(userEmail).child("Taste_Favorite").setValue(userTasteFavoriteInfo)
                }
                else{
                    Toast.makeText(this@JoininFoodFavoriteInfo, "Database Error, UserEmail", Toast.LENGTH_SHORT).show()
                }


                // Go to Map(Main) Page
                val intent = Intent(this@JoininFoodFavoriteInfo,Map::class.java)
                startActivity(intent)
            }
        })
    }

    override fun onBackPressed() {
        val intent = Intent(this@JoininFoodFavoriteInfo, Login::class.java)
        startActivity(intent)
    }
}

data class UserTasteFavoriteBaseInfo(
    var Saltiness : Int?=0,
    var Sourness : Int?=0,
    var Sweetness : Int?=0,
    var Savory : Int?=0,
    var Spiciness : Int?=0,
)