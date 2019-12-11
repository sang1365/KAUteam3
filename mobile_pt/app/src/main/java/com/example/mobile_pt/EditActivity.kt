package com.example.mobile_pt

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import com.example.mobile_pt.models.User
import com.example.mobile_pt.planner.Todo
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class EditActivity : AppCompatActivity() {

    var currentUser : User? = null
    val calendar : Calendar = Calendar.getInstance()  //날짜를 다룰 캘린더 객체

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        currentUser = intent.getParcelableExtra<User>("User")

        calendarView.setOnDateChangeListener{
            view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
        doneFab.setOnClickListener{
            insertTodo()
            finish()
        }
    }

    private fun insertTodo(){  //할일수정하는 메소드
        val ref = FirebaseDatabase.getInstance().getReference("/schedule/${currentUser!!.uid}").push()
        var updateItem : Todo = Todo( todoEditText.text.toString(), calendar.timeInMillis)
        ref.setValue(updateItem)
    }
}
