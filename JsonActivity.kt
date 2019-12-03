package com.example.mobile_pt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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


    private val database = FirebaseDatabase.getInstance()
    private val dataRef = database.getReference("users")
    val locheck = arrayListOf<location>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_json)

        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (dataSnapshot2 in dataSnapshot.children) { //하위노드가 없을 떄까지 반복
                    val lodata = dataSnapshot2.getValue(location::class.java)!!

                    locheck.add(lodata)
                }
                val size= locheck.size
                for(i in 0..(size-1)){

                    locatechecker(locheck[i].address)

                }


            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }
    fun locatechecker(locate : String?) {
        val JSON = "application/json; charset=utf-8".toMediaType()
        val address = FirebaseDatabase.getInstance().getReference("users").child("address")
        var url  = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=${locate}"
        Log.d("TAG", "sssssssssssssssssssssssssssssssssssss"+locate)
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
                  treeObject


            }

            override fun onFailure(call: Call, e: IOException) {



            }

        })



    }
}
