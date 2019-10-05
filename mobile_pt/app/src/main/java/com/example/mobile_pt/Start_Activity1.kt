package com.example.mobile_pt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.naver.maps.map.NaverMapSdk



class Start_Activity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient("at4cpkqv73")
        setContentView(R.layout.maplaylayout)

    }
}