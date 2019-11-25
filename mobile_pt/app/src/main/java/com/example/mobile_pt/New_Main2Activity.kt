package com.example.mobile_pt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

class New_Main2Activity : AppCompatActivity() {
    private val mFragmentManager = supportFragmentManager

    companion object{
        var currentUser: User? = null
        val TAG = "New_Main2Activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_new__main2)
        fetchCurrentUser()
        verifyUserIsLoggedIn()

        val dietFragment : DietFragment = DietFragment()
        val latestMessagesFragment : LatestMessagesFragment = LatestMessagesFragment()

        mFragmentManager.beginTransaction().replace( R.id.Frame_layout, dietFragment).commit()
        val getFragment = supportFragmentManager.findFragmentById(R.id.Frame_layout)
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        BottomList_Diet_d.setOnClickListener{
            //fragmentTransaction.replace(R.id.Frame_layout, DietFragment()).commit()
            mFragmentManager.beginTransaction().replace( R.id.Frame_layout, dietFragment).commit()
        }

        BottomList_Chat_d.setOnClickListener{ //채팅버튼 누르면 이동
            //fragmentTransaction.replace(R.id.Frame_layout, ChatLogFragment()).commit()
            mFragmentManager.beginTransaction().replace( R.id.Frame_layout, latestMessagesFragment).commit()
        }
        BottomList_Planner_d.setOnClickListener{ //플래너버튼 누르면 이동
            startActivity<PlannerActivity>()
        }
    }


    private fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                New_Main2Activity.currentUser = p0.getValue(User::class.java)
                Log.d("New_Main2Activity", "Current user ${New_Main2Activity.currentUser?.profileImageUrl}")
            }
            override fun onCancelled(p0: DatabaseError) {
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
    }
}
