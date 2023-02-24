package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    private lateinit var edit_email:EditText
    private lateinit var edit_password:EditText
    private lateinit var btn_login:Button
    private lateinit var btn_signup:Button
    private lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)
    edit_email=findViewById(R.id.edit_email)
        edit_password=findViewById(R.id.edit_password)
        btn_login=findViewById(R.id.btn_login)
        btn_signup=findViewById(R.id.btn_signup)
        mAuth= FirebaseAuth.getInstance()
        btn_signup.setOnClickListener {
            val intent=Intent(this,signup::class.java)
            startActivity(intent)
        }
        btn_login.setOnClickListener {
            val email=edit_email.text.toString()
            val password=edit_password.text.toString()
            login(email,password)
        }

    }
    private fun login(email:String,password:String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent=Intent(this@Login,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@Login, "user not exist", Toast.LENGTH_SHORT).show()
                }
            }

    }
}