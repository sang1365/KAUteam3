package com.example.mobile_pt

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.mobile_pt.messages.ChatLogActivity
import com.example.mobile_pt.models.User
import com.example.mobile_pt.views.remarkactivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.diet_fragment.*
import com.example.mobile_pt.New_Main2Activity
import java.util.*

class DietFragment :Fragment(), View.OnClickListener {
    var ctx : Context? = activity
    var dietcode : Int = 1
    var ToChatLogActivity : String = ""
    var currentUser : User? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ):
            View {
        val view : View = inflater!!.inflate(R.layout.diet_fragment, container, false)


        val Add_button1 = view.findViewById<ImageButton>(R.id.add_button1)
        val Add_button2 = view.findViewById<ImageButton>(R.id.add_button2)
        val Add_button3 = view.findViewById<ImageButton>(R.id.add_button3)

        val Remark_button1 = view.findViewById<ImageButton>(R.id.remark_button)
        val Remark_button2 = view.findViewById<ImageButton>(R.id.remark_button2)
        val Remark_button3 = view.findViewById<ImageButton>(R.id.remark_button3)

        var imageview_break = view.findViewById<ImageView>(R.id.Imageview_break)
        var imageview_lunch = view.findViewById<ImageView>(R.id.Imageview_lunch)
        var imageview_dinner = view.findViewById<ImageView>(R.id.Imageview_dinner)

        var storageref : StorageReference = FirebaseStorage.getInstance().getReference("images/breakfast")
        Glide.with(this).load(storageref).into(imageview_break)

        storageref = FirebaseStorage.getInstance().getReference("images/lunch")
        Glide.with(this).load(storageref).into(imageview_lunch)

        storageref = FirebaseStorage.getInstance().getReference("images/dinner")
        Glide.with(this).load(storageref).into(imageview_dinner)

        Add_button1.setOnClickListener(this)
        Add_button2.setOnClickListener(this)
        Add_button3.setOnClickListener(this)

        Remark_button1.setOnClickListener(this)
        Remark_button2.setOnClickListener(this)
        Remark_button3.setOnClickListener(this)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        ctx = activity
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        currentUser = New_Main2Activity.currentUser
        Log.d("DF", "Current user ${currentUser?.profileImageUrl}")
    }

    override fun onClick(v: View?) {
        var intent : Intent = Intent(ctx, remarkactivity :: class.java)

        when(v?.id){
            R.id.add_button1 ->{
                dietcode = 1
                opengallary()
            }
            R.id.add_button2 ->{
                dietcode = 2
                opengallary()
            }
            R.id.add_button3 ->{
                dietcode = 3
                opengallary()
            }
            R.id.remark_button->{
                intent.putExtra("dietname","아침 feedback : ")
                startActivityForResult(intent, 2)
            }
            R.id.remark_button2->{
                intent.putExtra("dietname","점심 feedback : ")
                startActivityForResult(intent, 2)
            }
            R.id.remark_button3->{
                intent.putExtra("dietname","저녁 feedback : ")
                startActivityForResult(intent, 2)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var currentImageUrl =data?.data
        selectedPhotoUri=data?.data
        val chatLogActivity : ChatLogActivity = ChatLogActivity()

        if(requestCode==1){
            val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver,currentImageUrl )
            uploadImageToFirebaseStorage() 
            if(dietcode==1)
            {
                Imageview_break.setImageBitmap(bitmap)
            }
            else if(dietcode==2)
            {
                Imageview_lunch.setImageBitmap(bitmap)
            }
            else
            {
                Imageview_dinner.setImageBitmap(bitmap)
            }
        }else if(requestCode==2){
            ToChatLogActivity=data!!.getStringExtra("remarkmessage").toString()
            chatLogActivity.SendFeedback(ToChatLogActivity)
            Log.d("Tochatlog",ToChatLogActivity)
        } else {
            Log.d("ActivityResult","something wrong")
        }
    }

    private fun opengallary(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, 1)
    }

    var selectedPhotoUri: Uri? = null
    private fun uploadImageToFirebaseStorage(){
        if(selectedPhotoUri == null)return
        var ref = FirebaseStorage.getInstance().getReference("/images")
        if(dietcode==1) {
            ref = FirebaseStorage.getInstance().getReference("/images/breakfast")
        }
        else if(dietcode==2){
            ref = FirebaseStorage.getInstance().getReference("/images/lunch")
        }
        else{
            ref = FirebaseStorage.getInstance().getReference("/images/dinner")
        }

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("RegisterActiviy","Successfully uploaded image:${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("RegisterActivity", "File Location: $it")
                }
            }
            .addOnFailureListener{
                //do some logging here
                Log.d("mainactivity","Failed to upload image to storage: ${it.message}")
            }
    }

    fun returnremark() : String{
        return ToChatLogActivity
    }
}