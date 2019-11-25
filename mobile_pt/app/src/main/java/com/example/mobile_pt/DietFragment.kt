package com.example.mobile_pt

import android.content.ContentResolver
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
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.diet_fragment.*
import java.lang.Exception
import java.util.*

class DietFragment :Fragment(), View.OnClickListener {
    var ctx : Context? = activity
    var dietcode : Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ):
            View {
        val view : View = inflater!!.inflate(R.layout.diet_fragment, container, false)

        val Add_button1 = view.findViewById<ImageButton>(R.id.add_button1)
        val Add_button2 = view.findViewById<ImageButton>(R.id.add_button2)
        val Add_button3 = view.findViewById<ImageButton>(R.id.add_button3)

        val Delete_button1 = view.findViewById<ImageButton>(R.id.delete_button1)
        val Delete_button2 = view.findViewById<ImageButton>(R.id.delete_button2)
        val Delete_button3 = view.findViewById<ImageButton>(R.id.delete_button3)

        Add_button1.setOnClickListener(this)
        Add_button2.setOnClickListener(this)
        Add_button3.setOnClickListener(this)

        Delete_button1.setOnClickListener(this)
        Delete_button2.setOnClickListener(this)
        Delete_button3.setOnClickListener(this)

        return view
    }

    companion object{
        fun newinstance(): DietFragment{
            return DietFragment()
        }
    }

    override fun onClick(v: View?) {
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
            R.id.delete_button1->{

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        ctx = activity
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var currentImageUrl =data?.data
        selectedPhotoUri=data?.data

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
        }
        else{
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
        val filename = UUID.randomUUID().toString()
        var ref = FirebaseStorage.getInstance().getReference("/images/example")
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
                Log.d(RegisterActivity.TAG,"Failed to upload image to storage: ${it.message}")
            }
    }
}