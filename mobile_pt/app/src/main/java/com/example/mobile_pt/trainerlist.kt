package com.example.mobile_pt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.trainerlist.*
import org.jetbrains.anko.startActivity

import com.google.firebase.database.DataSnapshot
import com.naver.maps.geometry.LatLng
import android.R.attr.data
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment


internal interface DataReceivedListener {
    fun onDataReceived(data: List<trainerdata>)
}


class TrainerList : Fragment(), View.OnClickListener {
    var ctx : Context? = activity

    val trainerlist2 = arrayListOf<trainerdata>()
    val myinfor = arrayListOf<trainerdata>()

    private val database = FirebaseDatabase.getInstance()
    private val dataRef = database.getReference("users")
    lateinit var mrecy: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater!!.inflate(R.layout.trainerlist, container, false)

        val mapbutton : Button = view.findViewById<Button>(R.id.mapbutton)

        mapbutton.setOnClickListener(this)

        val uid = FirebaseAuth.getInstance().uid
        val database = FirebaseDatabase.getInstance().reference
        val ref = database.child("/users/$uid")

        mrecy = view.findViewById(R.id.rv)
        val lm = LinearLayoutManager(ctx!!)
        mrecy.layoutManager = lm


        mrecy.setHasFixedSize(true)
        val intent : Intent = Intent(ctx, Profile::class.java)
        val madapter = traineradapter(ctx!!, trainerlist2) { trainerList ->
            intent.putExtra("trainer", trainerList)
            startActivity(intent)
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
                        Log.d("TAG", "SSSSSSSssssssss"+trainerlist2)
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
        return view
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.mapbutton -> {
                val intent: Intent = Intent(ctx, Start_Activity1::class.java)
                startActivity(intent)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        ctx = activity
        super.onCreate(savedInstanceState)
    }

    fun comparison (array : ArrayList<trainerdata> , array2 : ArrayList<trainerdata>){

        var sizes =array2.size

        for(i  in 0..(sizes-1)){
            array2[i].distance = LatLng(array[0].x!!,array[0].y!!).distanceTo(LatLng(array2[i].x!!,array2[i].y!!))
        }

        for(i in 0..(sizes-1)){
            if(array2[i].distance == 0.0){

                array2.removeAt(i)
                sizes--
                break;
            }
        }

        for (i in 0 .. (sizes- 1)) {
            for (j in 0 .. (sizes- 1)) {
                if (array2[i].distance!!< array2[j].distance!!) {
                    var temp = array2[i].distance
                    array2[i].distance = array2[j].distance
                    array2[j].distance = temp
                 }
            }
        }
    }
}
