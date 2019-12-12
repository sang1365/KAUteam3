package com.example.mobile_pt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.example.mobile_pt.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.profilelayout.*
import org.jetbrains.anko.startActivity


class Profile : AppCompatActivity() {
    var currentUser : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profilelayout)

        val uid = FirebaseAuth.getInstance().uid

        val trainer : trainerdata = intent.getParcelableExtra("trainer")

        val name = trainer.username
        val address = trainer.address
        val distance2 = trainer.distance
        val userid = trainer.uid
        adressview.setText(address)

        namebox.setText(name)
        distance.setText(distance2!!.toInt().toString())

        currentUser = New_Main2Activity.currentUser

        request.setOnClickListener {


            FirebaseDatabase.getInstance().getReference("/users/$uid").child("partner") .setValue(userid)
            startActivity<New_Main2Activity>()

        }



    }
}