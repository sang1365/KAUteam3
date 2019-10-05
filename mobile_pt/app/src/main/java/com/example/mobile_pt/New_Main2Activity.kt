package com.example.mobile_pt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_new__main2.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity

class New_Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new__main2)

        BottomList_Chat_d.setOnClickListener{ //채팅버튼 누르면 이동
            startActivity<ChatActivity>()
        }
        BottomList_Planner_d.setOnClickListener{ //플래너버튼 누르면 이동
            startActivity<PlannerActivity>()
        }
    }

}
