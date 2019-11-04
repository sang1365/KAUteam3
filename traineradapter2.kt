package com.example.mobile_pt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.listitem.*

import java.util.ArrayList

class traineradapter2 (val context: Context, val trainerList: ArrayList<trainerdata>) :
    RecyclerView.Adapter<traineradapter2.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.listitem, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return trainerList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(trainerList[position], context)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val Photo = itemView?.findViewById<ImageView>(R.id.iv_prof)
        val Breed = itemView?.findViewById<TextView>(R.id.iv_name)
        val Age = itemView?.findViewById<TextView>(R.id.iv_name2)


        fun bind (trainerList: trainerdata, context: Context) {
            if (trainerList.photo != "") {
                val resourceId = context.resources.getIdentifier(trainerList.photo, "drawable", context.packageName)
                Photo?.setImageResource(resourceId)
            } else {
                Photo?.setImageResource(R.mipmap.ic_launcher)
            }
            Breed?.text = trainerList.name
            Age?.text = trainerList.age

        }
    }
}