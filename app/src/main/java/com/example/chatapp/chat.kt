package com.example.chatapp

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class chat : AppCompatActivity() {
    private lateinit var chatrecyler:RecyclerView
    private lateinit var measagebox:EditText
    private lateinit var sendButton:ImageView
    private lateinit var meassage:measageAdpater
    private lateinit var measagelist:ArrayList<meassage>
    private lateinit var mbdr:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
var receiverroom:String?=null
        var senderroom:String?=null
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val name=intent.getStringExtra("name")
        val recvieruid=intent.getStringExtra("uid")
        var senderuid =FirebaseAuth.getInstance().currentUser?.uid
        mbdr=FirebaseDatabase.getInstance().getReference()
        senderroom=recvieruid+senderuid
        receiverroom=senderuid+recvieruid

        supportActionBar?.title=name
        chatrecyler=findViewById(R.id.recyler_view)
        measagebox=findViewById(R.id.measage_box)
        sendButton=findViewById(R.id.sent_btn)
        measagelist= ArrayList()
        meassage=measageAdpater(this,measagelist)
        chatrecyler.layoutManager=LinearLayoutManager(this)
        chatrecyler.adapter=meassage
        //logic for adding data to recyclerview
        //show message
        mbdr.child("chats").child(senderroom!!).child("measages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    measagelist.clear()
                    for (postsnapshot in snapshot.children){
                        val measage=postsnapshot.getValue(com.example.chatapp.meassage::class.java)
                        measagelist.add(measage!!)
                    }
                    meassage.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })
        sendButton.setOnClickListener {
            //This is a part responsible for programming the message. It does not appear in Database and does not appear as part of a screen
            val measage=measagebox.text.toString()
            val measageobject=meassage(measage,senderuid)
            mbdr.child("chats").child(senderroom!!).child("measages").push()
                .setValue(measageobject).addOnSuccessListener {

                    mbdr.child("chats").child(receiverroom!!).child("measages").push()
                        .setValue(measageobject)
                }
            measagebox.setText("")
        }
    }
}






