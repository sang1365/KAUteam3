package com.example.mobile_pt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.graphics.Bitmap
import android.provider.MediaStore
import com.example.mobile_pt.R.layout.activity_new__main2
import com.example.mobile_pt.messages.*
import kotlinx.android.synthetic.main.activity_new__main2.*
import org.jetbrains.anko.startActivity
import com.example.mobile_pt.models.User
import com.example.mobile_pt.registerlogin.RegisterActivity
import com.example.mobile_pt.views.LatestMessageRow
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.mobile_pt.messages.ChatLogActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.diet_fragment.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class New_Main2Activity : AppCompatActivity() {

    companion object{
        var currentUser: User? = null
        var partnerUser : User? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_new__main2)
        fetchCurrentUser()

        verifyUserIsLoggedIn()
        addresssave()

        val mFragmentManager = supportFragmentManager

        val dietFragment : DietFragment = DietFragment()
        val latestMessagesFragment : LatestMessagesFragment = LatestMessagesFragment()
        val plannerfragment : PlannerFragment = PlannerFragment()
        val trainerfragment : TrainerList = TrainerList()

        val getFragment = supportFragmentManager.findFragmentById(R.id.Frame_layout)
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        BottomList_Diet_d.setOnClickListener{
            fetchPartnerUser()
            mFragmentManager.beginTransaction().replace( R.id.Frame_layout, dietFragment).commit()
        }

        BottomList_Chat_d.setOnClickListener{ //채팅버튼 누르면 이동
            mFragmentManager.beginTransaction().replace( R.id.Frame_layout, latestMessagesFragment).commit()
        }
        BottomList_Planner_d.setOnClickListener{ //플래너버튼 누르면 이동
            mFragmentManager.beginTransaction().replace( R.id.Frame_layout, plannerfragment).commit()
        }
        BottomList_Member_d.setOnClickListener{
            mFragmentManager.beginTransaction().replace( R.id.Frame_layout, trainerfragment).commit()
            //startActivity<TrainerList>()
        }
    }


    private fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                New_Main2Activity.currentUser = p0.getValue(User::class.java)
                Log.d("New_Main2Activity", "Current user ${New_Main2Activity.currentUser?.profileImageUrl}")
                Log.d("partneruserid","Partner User id${currentUser?.partner}")
                Log.d("UID","UID url=${uid.toString()}")
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }

    fun fetchPartnerUser(){
        val partneridref = FirebaseDatabase.getInstance().getReference("/users/${New_Main2Activity.currentUser?.partner}")
        Log.d("partneruser","reference is ${partneridref.toString()}")
        partneridref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                New_Main2Activity.partnerUser = p0.getValue(User::class.java)
                Log.d("fetchToUser", "partner user ${New_Main2Activity.partnerUser?.profileImageUrl}")
            }
            override fun onCancelled(p0: DatabaseError) {
                Log.d("ToUser","Fetching To User failed")
            }
        })
    }

    private fun verifyUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        else{
            fetchPartnerUser()
        }
    }
    fun addresssave(){
        val addresslist = arrayListOf<User>(
        )
        val uid = FirebaseAuth.getInstance().uid
        val database = FirebaseDatabase.getInstance().reference
        val ref = database.child("/users/$uid")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val lodata = dataSnapshot.getValue(User::class.java)!!
                addresslist.add(lodata)
                locatechecker(addresslist[0].address)
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })


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
