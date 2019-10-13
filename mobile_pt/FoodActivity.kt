package com.example.mobile_pt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_food.*
import org.jetbrains.anko.startActivity

class FoodActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        BottomList_Chat_d.setOnClickListener{ //채팅버튼 누르면 이동
            startActivity<ChatActivity>()
        }
        BottomList_Planner_d.setOnClickListener{ //플래너버튼 누르면 이동
            startActivity<PlannerActivity>()
        }
    }

}
