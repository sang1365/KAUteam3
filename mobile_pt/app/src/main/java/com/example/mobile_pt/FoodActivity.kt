package com.example.mobile_pt

import android.accounts.Account
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_food.*
import org.jetbrains.anko.startActivity
import android.widget.EditText
import android.widget.ListView
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity

class FoodActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        var breakfastlist = ArrayList<String>()
        var lunchlist = ArrayList<String>()
        var dinnerlist = ArrayList<String>()

        val breakfastAdapter = DietListAdapter(this, breakfastlist)
        val lunchAdapter = DietListAdapter(this, lunchlist)
        val dinnerAdapter = DietListAdapter(this, dinnerlist)

        listview_break.adapter = breakfastAdapter
        listview_lunch.adapter = lunchAdapter
        listview_dinner.adapter = dinnerAdapter

        var editbreak = findViewById(R.id.input_breakfast) as EditText
        var editlunch = findViewById(R.id.input_lunch) as EditText
        var editdinner = findViewById(R.id.input_dinner) as EditText

        add_button1.setOnClickListener{
            breakfastlist.add( editbreak.text.toString())
            breakfastAdapter.notifyDataSetChanged()
        }

        delete_button1.setOnClickListener{
            breakfastlist.clear()
            breakfastAdapter.notifyDataSetChanged()
        }

        add_button2.setOnClickListener{
            lunchlist.add( editlunch.text.toString())
            breakfastAdapter.notifyDataSetChanged()
        }

        delete_button2.setOnClickListener{
            lunchlist.clear()
            lunchAdapter.notifyDataSetChanged()
        }

        add_button3.setOnClickListener{
            dinnerlist.add( editdinner.text.toString())
            breakfastAdapter.notifyDataSetChanged()
        }

        delete_button3.setOnClickListener{
            dinnerlist.clear()
            dinnerAdapter.notifyDataSetChanged()
        }
        BottomList_Chat_d.setOnClickListener{ //채팅버튼 누르면 이동
            startActivity<ChatActivity>()
        }
        BottomList_Planner_d.setOnClickListener{ //플래너버튼 누르면 이동
            startActivity<PlannerActivity>()
        }
    }

}
