package com.example.mobile_pt.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_pt.DietFragment
import com.example.mobile_pt.New_Main2Activity
import com.example.mobile_pt.R.layout.layout_remark
import com.example.mobile_pt.messages.ChatLogActivity
import com.example.mobile_pt.messages.LatestMessagesFragment
import kotlinx.android.synthetic.main.layout_remark.*

class remarkactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout_remark)
        val dietname : String = intent.getStringExtra("dietname")

        button.setOnClickListener{
            val remarkmessage : String = "$dietname${remarktext.text.toString()}"
            val intent = Intent(this, DietFragment::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("remarkmessage",remarkmessage)
            setResult(2,intent)
            finish()
        }
    }
}