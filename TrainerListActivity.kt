package com.example.mobile_pt


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.trainerlist.*
import org.jetbrains.anko.startActivity

import com.google.firebase.database.DataSnapshot
import com.naver.maps.geometry.LatLng

internal interface DataReceivedListener {
    fun onDataReceived(data: List<trainerdata>)
}


class TrainerListActivity : AppCompatActivity() {


    val trainerlist2 = arrayListOf<trainerdata>(
    )
    val myinfor = arrayListOf<trainerdata>(
    )

    private val database = FirebaseDatabase.getInstance()
    private val dataRef = database.getReference("users")
    lateinit var mrecy: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trainerlist)
        mapbutton.setOnClickListener {
            // 회원 찾으러 이동
            Log.d("valueQQQQQQQQQQ@@2", "change")
            startActivity<Start_Activity1>()


        }
        val uid = FirebaseAuth.getInstance().uid
        val database = FirebaseDatabase.getInstance().reference
        val ref = database.child("/users/$uid")

        mrecy = findViewById(R.id.rv)
        val lm = LinearLayoutManager(this)
        mrecy.layoutManager = lm


        mrecy.setHasFixedSize(true)
        val madapter = traineradapter2(this, trainerlist2) { trainerList ->
            startActivity<ProfileActivity>(

                "name" to trainerList.username,
                "address" to trainerList.address,
                "dinner" to trainerList.uid
            )

        }


        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                    val myinf = dataSnapshot.getValue(trainerdata::class.java)!!

                    myinfor.add(myinf)

                dataRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot2: DataSnapshot) {
                        trainerlist2.clear()
                        for (dataSnapshot3 in dataSnapshot2.children) { //하위노드가 없을 떄까지 반복
                            val trainerdats = dataSnapshot3.getValue(trainerdata::class.java)!!

                            trainerlist2.add(trainerdats)

                        }
                       comparison(myinfor,trainerlist2)
                        madapter.notifyDataSetChanged()
                    }

                    override fun onCancelled(p0: DatabaseError) {
                    }
                })


            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })

        mrecy.adapter = madapter


    }
    fun comparison (array : ArrayList<trainerdata> , array2 : ArrayList<trainerdata>){

        var posit = LatLng(array[0].x!!,array[0].y!!)
        var posit2 = LatLng(array2[0].x!!,array2[0].y!!)
        var posit3 = LatLng(array2[1].x!!,array2[1].y!!)

        if(posit.distanceTo(posit2)==0.0) {
            array2.removeAt(0)

            array2[0].distance = posit.distanceTo(posit3)
        }
        else if(posit.distanceTo(posit3)==0.0){

            array2.removeAt(1)
            array[0].distance = posit.distanceTo(posit2)
        }


    }

}
