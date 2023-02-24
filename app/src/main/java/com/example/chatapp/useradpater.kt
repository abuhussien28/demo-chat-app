package com.example.chatapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter( val context: Context, val userlist :ArrayList<user>)
    : RecyclerView.Adapter<CustomAdapter.UserVH>()
    {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        val view: View = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

        return UserVH(view)
    }

  override fun onBindViewHolder(holder: UserVH, position: Int) {
val currentuser=userlist[position]
      holder.textname?.text=currentuser.name
holder.itemView.setOnClickListener {
    val intent=Intent(context,chat::class.java)
    intent.putExtra("name",currentuser.name)
    intent.putExtra("uid",currentuser.uid)
    context.startActivity(intent)
}
    }
    override fun getItemCount(): Int =userlist.size
        class UserVH(View:View):RecyclerView.ViewHolder(View) {
            val textname=View.findViewById<TextView>(R.id.t_v)
        }

    }

