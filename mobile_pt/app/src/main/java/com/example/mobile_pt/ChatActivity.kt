package com.example.mobile_pt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.main_chat.*

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_chat)

        BottomList_Diet.setOnClickListener{
            val intent1= Intent(this, MainActivity::class.java)
            startActivity(intent1)
        }

        BottomList_Planner.setOnClickListener{
            val intent2= Intent(this,PlanActivity::class.java)
            startActivity(intent2)
        }
    }
}
