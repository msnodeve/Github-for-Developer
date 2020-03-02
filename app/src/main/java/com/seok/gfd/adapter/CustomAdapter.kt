package com.seok.gfd.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.seok.gfd.R

class CustomAdapter(personNames : ArrayList<String>, personImages : ArrayList<Int>, context: Context) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    var personNames =personNames
    var personImages : ArrayList<Int> = personImages
    var context: Context = context

    override fun getItemCount() = personNames.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rowlayout, parent, false)
        return MyViewHolder(v)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = personNames[position]
        holder.image.setImageResource(personImages[position])
        holder.itemView.setOnClickListener {
            Toast.makeText(context, personNames[position], Toast.LENGTH_SHORT).show()
        }
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var name : TextView = itemView.findViewById(R.id.name)
        var image : ImageView = itemView.findViewById(R.id.image)
    }

}

