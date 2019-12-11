package com.example.mobile_pt.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val uid: String, val username: String,val partner : String?,val address: String, val profileImageUrl: String, val x : Double, val y : Double): Parcelable {
    constructor() : this("", "", "","","",0.0,0.0)
}