package com.example.mobile_pt.planner

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Todo (  //Realm에서 사용하는 클래스에 open키워드 추가
    @PrimaryKey var id: Long = 0, //id는 유일한 값이 되어야하기 때문에 기본키 primary key 제약을 추가
    var title : String = "",
    var date: Long = 0
    ): RealmObject(){ //RealmObject 클래스를 상속받아 Realm데이터베이스에서 다룰 수 있다.

}