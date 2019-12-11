package com.example.mobile_pt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_json.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.net.URL
import java.util.ArrayList
import org.json.JSONArray


class JsonActivity : AppCompatActivity() {

    var addresslist = arrayListOf<location2>(
    )

    private val database = FirebaseDatabase.getInstance()
    private val dataRef = database.getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_json)
        val uid = FirebaseAuth.getInstance().uid
        val database = FirebaseDatabase.getInstance().reference
        val ref = database.child("/users/$uid")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                    val lodata = dataSnapshot.getValue(location2::class.java)!!
                    addresslist.add(lodata)
                    locatechecker(addresslist[0].address)



            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })
        Log.d("Tag","dd!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1d" + addresslist)
    }
    fun locatechecker(locate : String?) {

        val uid = FirebaseAuth.getInstance().uid
        val JSON = "application/json; charset=utf-8".toMediaType()
        val address = FirebaseDatabase.getInstance().getReference("users").child("address")
        var url  = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=${locate}"

        val client = OkHttpClient()
        var json = JSONObject()
        var gson = Gson()
        val body = json.toString().toRequestBody(JSON)
        val request = Request.Builder()
            .header("X-NCP-APIGW-API-KEY-ID","at4cpkqv73")
            .addHeader("X-NCP-APIGW-API-KEY", "4Cow7JB0diWmzaAHGyIpHpGBz8yMeAvpBF5YHRjo")
            .url(url)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                var str = response?.body?.string()

                Log.d("TAG", " here2!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1"+ str)
                val jObject = JSONObject(str)
                val address = jObject.getString("addresses")
                val jArray = JSONArray(address)
                val treeObject = jArray.getJSONObject(0)
                val latitude = treeObject.getDouble("x")
                val longitude =treeObject.getDouble("y")
                FirebaseDatabase.getInstance().getReference("/users/$uid").child("x") .setValue(latitude)
                FirebaseDatabase.getInstance().getReference("/users/$uid").child("y") .setValue(longitude)


            }

            override fun onFailure(call: Call, e: IOException) {



            }

        })



    }

}
