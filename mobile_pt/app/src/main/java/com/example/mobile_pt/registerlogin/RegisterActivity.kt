package com.example.mobile_pt.registerlogin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.mobile_pt.messages.LatestMessagesActivity
import com.example.mobile_pt.R
import com.example.mobile_pt.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*
class RegisterActivity: AppCompatActivity() {

    companion object{
        val TAG = "RegisterActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_button_register.setOnClickListener {
           performRegister()

        }
        already_have_account_text_view.setOnClickListener {
            Log.d(TAG, "Try to show login activity")

            //launch the login activity somehow
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        selectphoto_button_register.setOnClickListener{
            Log.d("RegisterActivity", "Try to show photo selector")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    var selectedPhotoUri: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            //proceed and check what the selected image was..
            Log.d("RegisterActivity", "Photo was selected")

            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            selectphoto_imageview_register.setImageBitmap(bitmap)

            selectphoto_button_register.alpha = 0f
           // val bitmapDrawable = BitmapDrawable(bitmap)
            //selectphoto_button_register.setBackgroundDrawable(bitmapDrawable)
        }
    }
    private fun performRegister(){
        val email = email_edittext_register.text.toString()
        val password = password_edittext_register.toString()
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Please enter text in email/pw", Toast.LENGTH_SHORT).show()
            return
        }
        Log.d("RegisterActivity", "Email is : " + email)
        Log.d("RegisterActivity", "Password : " + password)

        //Firebase Authentication to create a user with email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(!it.isSuccessful)return@addOnCompleteListener

                //else if successful
                Log.d(TAG, "Successfully created user with uid:" + (it.result?.user?.uid))

                uploadImageToFirebaseStorage()
            }
            .addOnFailureListener{
                Log.d("RegisterActivity", "Failed to create user:"+ it.message)
                Toast.makeText(this,"Failed to create user:"+ it.message, Toast.LENGTH_SHORT).show()
            }
    }
    private fun uploadImageToFirebaseStorage(){
        if(selectedPhotoUri == null) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("RegisterActiviy","Successfully uploaded image:${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("RegisterActivity", "File Location: $it")
                    saveUserToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener{
                //do some logging here
                Log.d(TAG,"Failed to upload image to storage: ${it.message}")
            }
    }
    private fun saveUserToFirebaseDatabase(profileImageUri: String)
    {
        val uid = FirebaseAuth.getInstance().uid ?:""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user = User(uid, username_edittext_register.text.toString(),address_edittext_register.text.toString(), profileImageUri)
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("RegisterActivity", "Finally we saved the user to Firebase Database")

                //로그인 성공 후 이동하는 액티비티
                val intent = Intent(this, LatestMessagesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }
            .addOnFailureListener {
                Log.d("RegisterActivity", "Failed to set value to database: ${it.message}")
            }
    }
}
