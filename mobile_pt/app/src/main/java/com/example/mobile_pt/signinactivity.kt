package com.example.mobile_pt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.profile.*
import org.jetbrains.anko.startActivity

class signinactivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)
        training.setOnClickListener{ //트레이너 찾으러 이동
            startActivity<TrainerListActivity>()
        }
        trainer.setOnClickListener{ // 회원 찾으러 이동

            startActivity<signinactivity>()


        }
    }
}
