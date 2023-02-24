package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class measageAdpater(val context:Context,val measagelist:ArrayList<meassage>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
val item_receive=1
    val item_sent=2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       if (viewType==1){
           val view: View = LayoutInflater.from(context).inflate(R.layout.receive, parent, false)

           return reciveViewholder(view)
       }
        else{
           val view: View = LayoutInflater.from(context).inflate(R.layout.send, parent, false)

           return sentViewholder(view)
       }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentuser=measagelist[position]
        if(holder.javaClass==sentViewholder::class.java){
val viewholder=holder as sentViewholder
            holder.sent_maeasage.text=currentuser.meassage
        }
        else{
            val viewholder=holder as reciveViewholder
            holder.recive_maeasage.text=currentuser.meassage
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentuser=measagelist[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentuser.senderId)){
            return item_sent
        }
        else{
            return item_receive
        }
    }
    override fun getItemCount(): Int {
     return  measagelist.size
    }
    class sentViewholder(itemview: View):RecyclerView.ViewHolder(itemview){
val sent_maeasage=itemview.findViewById<TextView>(R.id.message_sent)
    }
    class reciveViewholder(itemview: View):RecyclerView.ViewHolder(itemview){
        val recive_maeasage=itemview.findViewById<TextView>(R.id.message_recive)
    }
    }