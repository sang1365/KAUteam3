package com.example.mobile_pt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.start_layout2.*
import kotlinx.android.synthetic.main.trainerlist.*
import org.jetbrains.anko.startActivity

class trainerlist : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trainerlist)

        mapbutton.setOnClickListener{ // 회원 찾으러 이동

            startActivity<Start_Activity1>()


        }
    }
}
