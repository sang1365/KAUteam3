package com.example.mobile_pt

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.lang.reflect.Constructor

@Parcelize
data class trainerdata(val address: String? = null, val username: String? = null, val uid: String?= null, val x : Double? =null, val y: Double? = null, var distance : Double? =null) : Parcelable{
     constructor() : this("","","",0.0,0.0,0.0)
}