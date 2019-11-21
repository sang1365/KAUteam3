package com.example.mobile_pt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import androidx.annotation.Nullable
import org.jetbrains.anko.startActivity



class SplashActivity : AppCompatActivity() {

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity<RegisterActivity>()


        finish()
    }
}