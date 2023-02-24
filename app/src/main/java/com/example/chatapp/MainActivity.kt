package com.example.chatapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var recyler:RecyclerView
    private lateinit var userlist:ArrayList<user>
    private lateinit var  mAuth: FirebaseAuth
    private lateinit var mDbr: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth= FirebaseAuth.getInstance()
        mDbr=FirebaseDatabase.getInstance().getReference()
        userlist= ArrayList()

        recyler=findViewById(R.id.r_v)

        val adapter=CustomAdapter(this,userlist)
        recyler.adapter=adapter
        mDbr= FirebaseDatabase.getInstance().getReference()
        mDbr.child("user").addValueEventListener(object :ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
//userlist.clear()
                for (postsnapshot in snapshot.children){
                    val currentuser=postsnapshot.getValue(user::class.java)
                    if (mAuth.currentUser?.uid!=currentuser?.uid){
                        userlist.add(currentuser!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.layout){
            mAuth.signOut()
            val intent=Intent(this@MainActivity,Login::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }
}