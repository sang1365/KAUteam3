package com.example.mobile_pt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.trainerlist.*
import org.jetbrains.anko.startActivity
import com.google.firebase.database.ValueEventListener as ValueEventListener1

class TrainerActivity : AppCompatActivity() {


    lateinit var database : FirebaseDatabase
    lateinit var dataref : DatabaseReference
    var trainerlist2 = arrayListOf<trainerdata>(
        trainerdata("홍길동","22",""),
        trainerdata("정상수","23",""),
        trainerdata("김홍식","24",""),
        trainerdata("조형기","25","")





    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trainerlist)

        val mAdapter = traineradapter2(this, trainerlist2)
        rv.adapter = mAdapter

        val lm = LinearLayoutManager(this)
        rv.layoutManager = lm
        rv.setHasFixedSize(true)

        database = FirebaseDatabase.getInstance()//파베연동

        dataref = database.getReference("User")
      





        mapbutton.setOnClickListener{ // 회원 찾으러 이동

            startActivity<Start_Activity1>()


        }

    }
}
