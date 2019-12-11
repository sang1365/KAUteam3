package com.example.mobile_pt

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.constraintlayout.widget.Constraints
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
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
import kotlinx.android.synthetic.main.maplaylayout.*
import java.io.IOException
import java.security.AccessController.getContext
import java.util.concurrent.CountDownLatch


class Start_Activity1 : AppCompatActivity() , OnMapReadyCallback {
    private lateinit var locationSource: FusedLocationSource

    var point = arrayListOf<location2>(
    )

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
                            val lodata2 = dataSnapshot2.getValue(location2::class.java)!!
                            point.add(lodata2)
                            Log.d("TAG" , "correct? 22222222222222222222222233333333333333"  + lodata2)


                        }

                        val size  = point.size
                        for( i in 0..(size-1)){
                            marker[i]!!.position = LatLng(point[i].y!!, point[i].x!!)
                            marker[i]!!.map = naverMap

                        }


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
        val size = ArrayList<LatLng>()

    }



}