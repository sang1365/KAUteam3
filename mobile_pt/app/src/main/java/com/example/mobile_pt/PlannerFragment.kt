package com.example.mobile_pt

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.mobile_pt.planner.Todo
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.ListFragment
import com.example.mobile_pt.*
import com.example.mobile_pt.messages.ChatLogActivity
import com.example.mobile_pt.models.ChatMessage
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import io.realm.Sort
import com.example.mobile_pt.models.User
import com.example.mobile_pt.planner.plannerArrayAdapter
import com.example.mobile_pt.views.ChatFromItem
import com.example.mobile_pt.views.ChatToItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.activity_planner.*
import org.jetbrains.anko.startActivity
import java.util.ArrayList

class PlannerFragment: Fragment() , View.OnClickListener{


    var currentUser : User? = null
    var ctx : Context? = activity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater!!.inflate(R.layout.activity_planner, container, false)

        val todo : ArrayList<Todo> = ArrayList<Todo>()
        val ref = FirebaseDatabase.getInstance().getReference("/schedule/${currentUser!!.uid}")
        val fromId = FirebaseAuth.getInstance().uid

        ref.addChildEventListener(object: ChildEventListener {

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val gettodo = p0.getValue(Todo::class.java)
                if (todo != null) {
                    todo.add(gettodo!!)
                    Log.d("plannerfragmentcontent",todo[0].title + todo[0].date)
                }
            }
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }
        })
        val listView : ListView = view.findViewById(R.id.planlist)
        val adapter = plannerArrayAdapter(ctx!!, todo)
        listView.adapter = adapter
        adapter.notifyDataSetChanged()

        val button = view.findViewById<Button>(R.id.addbutton)

        button.setOnClickListener {
            val intent : Intent = Intent(activity, EditActivity::class.java)
            intent.putExtra("User",currentUser)
            startActivity(intent)
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        currentUser = New_Main2Activity.currentUser
        Log.d("PF", "Current user ${currentUser?.profileImageUrl}")
    }

    override fun onClick(p0: View?) {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ctx=activity
    }

}
