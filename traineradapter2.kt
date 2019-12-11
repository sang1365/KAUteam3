package com.example.mobile_pt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.listitem.*
import org.jetbrains.anko.startActivity
import java.util.ArrayList
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent



class traineradapter2 (val context: Context, val trainerList: ArrayList<trainerdata>,val itemClick: (trainerdata) -> Unit) :

    RecyclerView.Adapter<traineradapter2.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.listitem, parent, false)
        return Holder(view,itemClick)
    }

    override fun getItemCount(): Int {
        return trainerList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(trainerList[position], context)


    }


    inner class Holder(itemView: View?, itemClick: (trainerdata) -> Unit): RecyclerView.ViewHolder(itemView!!) {
        val Photo = itemView?.findViewById<ImageView>(R.id.iv_prof)
        val name= itemView?.findViewById<TextView>(R.id.iv_name)
        val Age = itemView?.findViewById<TextView>(R.id.iv_name2)
        val distance = itemView?.findViewById<TextView>(R.id.iv_dis)


        fun bind (trainerList: trainerdata, context: Context) {
            if (trainerList.uid != "") {
                val resourceId = context.resources.getIdentifier(trainerList.uid, "drawable", context.packageName)
                Photo?.setImageResource(resourceId)
            } else {
                Photo?.setImageResource(R.mipmap.ic_launcher)
            }
            name?.text = trainerList.username
            distance?.text = trainerList.distance.toString()
            Age?.text = trainerList.address
            itemView.setOnClickListener { itemClick(trainerList) }

        }
    }
}