package com.example.mobile_pt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    lateinit  var addressList: ArrayList<addressdata>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_json)
        var addressList = addressdata()
        val JSON = "application/json; charset=utf-8".toMediaType()
        var url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=연사리"
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
                addressList.x = treeObject.getDouble("x")
                addressList.y = treeObject.getString("y")
                Log.d("TAG", " here2!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1"+ addressList.x+"띠고"+addressList.y)

            }

            override fun onFailure(call: Call, e: IOException) {



            }

        })
    }

}
