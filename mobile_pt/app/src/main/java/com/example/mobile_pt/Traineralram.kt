package com.example.mobile_pt



import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class Traineralram : AppCompatActivity()  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_layout)

        val create=findViewById<Button>(R.id.request)

        create.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v : View?){
                createNotification()
            }
        })

    }
    //git 테스트

    fun createNotification(){
        val builder : NotificationCompat.Builder = NotificationCompat.Builder(this,"default")
        builder.setSmallIcon(R.mipmap.ic_launcher) //아이콘
        builder.setContentTitle("Mobile PT") //세부제목
        builder.setContentText("신청이 완료되었습니다") //세부내용
        builder.setColor(Color.RED) //알림 색깔?
        builder.setAutoCancel(true)     //사용자가 탭을 클릭하면 자동 닫기
        //알림생성
        val notificationManager : NotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            notificationManager.createNotificationChannel(NotificationChannel("default","기본채널",NotificationManager.IMPORTANCE_DEFAULT))
        }
        // id값은
        // 정의해야하는 각 알림의 고유한 int값
        notificationManager.notify(1, builder.build())
    }
    fun removeNotification(){
        NotificationManagerCompat.from(this).cancel(1);
    }

}