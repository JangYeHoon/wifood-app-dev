package com.example.wifood.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wifood.adapter.DeleteFoodGroupAdapter
import com.example.wifood.databinding.ActivityDeleteFoodGroupBinding
import com.example.wifood.entity.Group

class DeleteFoodGroup : AppCompatActivity() {
    lateinit var binding : ActivityDeleteFoodGroupBinding
    lateinit var deleteFoodGroupAdapter : DeleteFoodGroupAdapter
    var foodGroupList = mutableListOf<Group>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteFoodGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nameList = intent.getStringArrayListExtra("groupName")
        val idList = intent.getIntegerArrayListExtra("groupId")
        val colorList = intent.getStringArrayListExtra("groupColor")
        for (i in 0 until nameList!!.count())
            foodGroupList.add(Group(idList!![i], nameList[i], colorList!![i]))

        deleteFoodGroupAdapter = DeleteFoodGroupAdapter(this)
        binding.deleteRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.deleteRecyclerView.adapter = deleteFoodGroupAdapter
        deleteFoodGroupAdapter.setListData(foodGroupList)

        binding.deleteBtn.setOnClickListener {
            val intent = Intent().apply {
                putExtra("id", ArrayList(deleteFoodGroupAdapter.getDeleteFoodIdList()))
                putExtra("type", 2)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}