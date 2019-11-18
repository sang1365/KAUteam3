package com.example.mobile_pt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class DietListAdapter(var context: Context,var diet: ArrayList<String>) : BaseAdapter() {

    override fun getView(idx: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.dietlistview,parent,false) as View
        view.findViewById<TextView>(R.id.diet_text).text = diet[idx]

        return view
    }


    override fun getItem(position: Int): Any {
        return diet[position]
    }

    override fun getItemId(idx: Int): Long {
        return idx.toLong()
    }

    override fun getCount(): Int {
        return diet.size
    }

}