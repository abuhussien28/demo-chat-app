package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signup : AppCompatActivity() {
    private lateinit var editemail: EditText
    private lateinit var editpassword: EditText
    private lateinit var btnsignup: Button
    private lateinit var editname:EditText
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbr:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.hide()
        editemail=findViewById(R.id.edit_email)
        editpassword=findViewById(R.id.edit_password)
        btnsignup=findViewById(R.id.btn_signup)
        editname=findViewById(R.id.edit_name)
        mAuth= FirebaseAuth.getInstance()
        btnsignup.setOnClickListener {
            val name=editname.text.toString()
            val email=editemail.text.toString()
            val password=editpassword.text.toString()
            sigup(name,email,password)
        }
    }
    private fun sigup( name :String ,email:String, password:String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                        addusertodatabase(name,email,mAuth.currentUser?.uid!!)
val intent=Intent(this@signup,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@signup, "some error exixt", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun addusertodatabase(name: String, email: String, uid: String) {
mDbr=FirebaseDatabase.getInstance().getReference()
        mDbr.child("User").child(uid).setValue(user(name,email,uid))
    }


}