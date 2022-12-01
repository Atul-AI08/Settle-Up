package com.example.settle_up

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase

class ShowGroups : AppCompatActivity() {
    private lateinit var recyclerView : RecyclerView
    private lateinit var groupAdapter: GroupAdapter
    private lateinit var data : ArrayList<String>
    private lateinit var db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_groups)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        data = arrayListOf()
        groupAdapter = GroupAdapter(data)
        recyclerView.adapter = groupAdapter
        eventChangeListener()
    }
    private fun eventChangeListener() {
        db = FirebaseFirestore.getInstance()
        val user = Firebase.auth.currentUser?.uid.toString()
        db.collection("Users").document(user).get().addOnSuccessListener { document ->
            val email = document["email"].toString()
            db.collection("UserGroups").document(email).get().addOnSuccessListener { document ->
                if (document != null) {
                    val map = document.data
                    if (map != null) {
                        for (s in map.keys){
                            data.add(s)
                            groupAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }
}