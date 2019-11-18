package com.example.mobile_pt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobile_pt.planner.PlannerActivity
import kotlinx.android.synthetic.main.trainerlist.*
import org.jetbrains.anko.startActivity

class TrainerListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trainerlist)

        mapbutton.setOnClickListener{ // 회원 찾으러 이동
            startActivity<Start_Activity1>()

        }
        BottomList_Diet_trainer.setOnClickListener{
            startActivity<FoodActivity>()
        }
        BottomList_Chat_trainer.setOnClickListener{
            startActivity<ChatActivity>()
        }
        BottomList_Planner_trainer.setOnClickListener{
            startActivity<PlannerActivity>()
        }
    }
}
