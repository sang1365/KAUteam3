package com.example.mobile_pt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_new__main2.*
import kotlinx.android.synthetic.main.signlayout.*
import org.jetbrains.anko.startActivity

class signactivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signlayout)

        user.setOnClickListener{ //회원가입누르면 이동
            startActivity<signinactivity>()
        }
        login.setOnClickListener{ //로그인 누르면 이동
            startActivity<New_Main2Activity>()
        }
    }
}
