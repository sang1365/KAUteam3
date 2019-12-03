package com.example.mobile_pt


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.trainerlist.*
import org.jetbrains.anko.startActivity

import com.google.firebase.database.DataSnapshot



class TrainerListActivity : AppCompatActivity() {





    var trainerlist2 = arrayListOf<trainerdata>(
    )

    private val database = FirebaseDatabase.getInstance()
    private val dataRef = database.getReference("users")
    lateinit var mrecy : RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trainerlist)
        mapbutton.setOnClickListener{ // 회원 찾으러 이동

            startActivity<Start_Activity1>()


        }
        mrecy = findViewById(R.id.rv)
        val lm = LinearLayoutManager(this)
        mrecy.layoutManager = lm

        mrecy.setHasFixedSize(true)
        val madapter = traineradapter2(this,trainerlist2){
                trainerList ->  startActivity<ProfileActivity>(

            "name" to trainerList.name,
            "address" to trainerList.address,
            "dinner" to trainerList.dinner
        )





        }




        dataRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                trainerlist2.clear()


                for (dataSnapshot2 in dataSnapshot.children) { //하위노드가 없을 떄까지 반복


                    val trainerdats = dataSnapshot2.getValue(trainerdata::class.java)!!
                    Log.d("value@@@@@@@@@2", "change"+trainerdats)
                    Log.d("value@@@@@@@@@2", "change"+dataSnapshot2.child("address"))


                    trainerlist2.add(trainerdats)



                }

                madapter.notifyDataSetChanged()
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })

        mrecy.adapter = madapter











    }
}