package com.example.mobile_pt.planner

import android.app.Application
import io.realm.Realm

class MyPlanner:Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}