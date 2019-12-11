package com.example.mobile_pt.planner

import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class Todo (val title : String , val date : Long): Parcelable{
    constructor() : this( "", 0)
}