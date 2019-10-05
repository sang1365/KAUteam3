package com.example.myapplication

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast


import android.location.LocationListener
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.FusedLocationSource


class MainActivity : AppCompatActivity() , OnMapReadyCallback {


    private lateinit var locationSource: FusedLocationSource


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.maplayout)
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
        val marker = Marker()
        val posit = LatLng(37.5670135, 126.9783740)

        marker.position = posit
        marker.map = naverMap
        val path = PathOverlay()


        naverMap.locationSource = locationSource

            naverMap.addOnLocationChangeListener { location->
                Toast.makeText(this, "${posit.distanceTo(LatLng(location.latitude, location.longitude))}",
                    Toast.LENGTH_SHORT).show()
                path.coords = listOf(
                    posit,
                    LatLng(location.latitude,location.longitude)
                )
                path.map = naverMap


            }
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
    }


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }


}

