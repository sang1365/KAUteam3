package com.example.mobile_pt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.FusedLocationSource
import java.util.concurrent.CountDownLatch


class Start_Activity1 : AppCompatActivity() , OnMapReadyCallback {
    private lateinit var locationSource: FusedLocationSource

    val point = arrayListOf<locations>(
    )
    val myinfor: ArrayList<locations> = arrayListOf<locations>()

    var request : Boolean = true

    private val database = FirebaseDatabase.getInstance()
    private val dataRef = database.getReference("users")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.maplaylayout)
        locationSource =
            FusedLocationSource(this,  LOCATION_PERMISSION_REQUEST_CODE)
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions,
                grantResults)) {
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(naverMap: NaverMap) {

        val mark = Marker()

        var marker = arrayOfNulls<Marker>(100)

        val posit = LatLng(37.5670135, 126.9783740)
        val posit2= LatLng(37.560135, 126.978340)

        val uid = FirebaseAuth.getInstance().uid
        val database = FirebaseDatabase.getInstance().reference
        val ref = database.child("/users/$uid")
        val latch = CountDownLatch(1)


        for(i in 0..99){

            marker[i] = Marker()


        }

        val path = PathOverlay()

        naverMap.locationSource = locationSource

        naverMap.addOnLocationChangeListener { location->

            if(request ==true) {


                dataRef.addValueEventListener(object : ValueEventListener {


                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        for (dataSnapshot2 in dataSnapshot.children) {
                            val lodata2 = dataSnapshot2.getValue(locations::class.java)!!
                            point.add(lodata2)
                            Log.d("TAG" , "correct? 22222222222222222222222233333333333333"  + lodata2)


                        }
                        ref.addValueEventListener(object : ValueEventListener {


                            override fun onDataChange(dataSnapshot3: DataSnapshot) {

                                val lodata3 = dataSnapshot3.getValue(locations::class.java)!!
                                myinfor.add(lodata3)
                                val size  = point.size
                                for( i in 0..(size-1)){
                                    if(LatLng(point[i].y!!, point[i].x!!) == LatLng(myinfor[0].y!!, myinfor[0].x!!) ){


                                        }
                                    else {
                                        marker[i]!!.position = LatLng(point[i].y!!, point[i].x!!)
                                        marker[i]!!.map = naverMap
                                        Log.d("Tag","sssssssssssssssssssssssssssssssss" + point[i])
                                    }
                                }
                            }
                            override fun onCancelled(p0: DatabaseError) {
                            }
                        })
                    }
                    override fun onCancelled(p0: DatabaseError) {
                    }
                })


                FirebaseDatabase.getInstance().getReference("/users/$uid").child("mylocation-x")
                    .setValue(location.latitude)
                FirebaseDatabase.getInstance().getReference("/users/$uid").child("mylocation-y")
                    .setValue(location.longitude)
                request =false
            }



        }




        naverMap.locationTrackingMode = LocationTrackingMode.Follow

    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000


    }



}