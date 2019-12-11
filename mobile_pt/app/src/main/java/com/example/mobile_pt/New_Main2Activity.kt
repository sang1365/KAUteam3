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
import kotlinx.android.synthetic.main.diet_fragment.*

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

        val mFragmentManager = supportFragmentManager

        val dietFragment : DietFragment = DietFragment()
        val latestMessagesFragment : LatestMessagesFragment = LatestMessagesFragment()
        val plannerfragment : PlannerFragment = PlannerFragment()

        val getFragment = supportFragmentManager.findFragmentById(R.id.Frame_layout)
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        BottomList_Diet_d.setOnClickListener{
            //fragmentTransaction.replace(R.id.Frame_layout, DietFragment()).commit()
            fetchPartnerUser()
            mFragmentManager.beginTransaction().replace( R.id.Frame_layout, dietFragment).commit()
        }

        BottomList_Chat_d.setOnClickListener{ //채팅버튼 누르면 이동
            //fragmentTransaction.replace(R.id.Frame_layout, ChatLogFragment()).commit()
            mFragmentManager.beginTransaction().replace( R.id.Frame_layout, latestMessagesFragment).commit()
        }
        BottomList_Planner_d.setOnClickListener{ //플래너버튼 누르면 이동
            mFragmentManager.beginTransaction().replace( R.id.Frame_layout, plannerfragment).commit()
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
}
