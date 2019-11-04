package com.example.mobile_pt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.signlayout.*
import kotlinx.android.synthetic.main.start_layout2.*
import org.jetbrains.anko.startActivity

class signinactivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_layout2)
        training.setOnClickListener{ //트레이너 찾으러 이동
            startActivity<trainerlist>()
        }
        trainer.setOnClickListener{ // 회원 찾으러 이동

            startActivity<signinactivity>()


        }
    }
}
