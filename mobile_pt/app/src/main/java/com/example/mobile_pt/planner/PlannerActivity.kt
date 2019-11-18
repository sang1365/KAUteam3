package com.example.mobile_pt.planner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.mobile_pt.*
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where

import kotlinx.android.synthetic.main.activity_planner.*
import kotlinx.android.synthetic.main.content_planner.*
import org.jetbrains.anko.startActivity

class PlannerActivity: AppCompatActivity() {

    val realm = Realm.getDefaultInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner)
        setSupportActionBar(toolbar)

        val realmResult = realm.where<Todo>()
            .findAll()
            .sort("date", Sort.DESCENDING)
        val adapter = TodoListAdapter(realmResult)
        listView.adapter = adapter

        realmResult.addChangeListener { _->adapter.notifyDataSetChanged() }
        listView.setOnItemClickListener{ parent, view, position, id->
            startActivity<EditActivity>("id" to id)
        }
        fab.setOnClickListener {
            startActivity<EditActivity>()
        }
        BottomList_Diet_p.setOnClickListener{
            startActivity<FoodActivity>()
        }
        BottomList_Chat_p.setOnClickListener{
            startActivity<ChatActivity>()
        }
        BottomList_Member_p.setOnClickListener{
            startActivity<TrainerListActivity>()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
