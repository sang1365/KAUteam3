package com.example.mobile_pt.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val uid: String, val username: String,val address:String, val profileImageUrl: String): Parcelable {
    constructor() : this("", "", "", "")
}