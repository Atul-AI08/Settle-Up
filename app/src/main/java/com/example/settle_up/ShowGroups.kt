package com.example.settle_up

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase

class ShowGroups : AppCompatActivity() {
    private lateinit var recyclerView : RecyclerView
    private lateinit var groupAdapter: GroupAdapter
    private lateinit var data :ArrayList<Groups>
    private lateinit var db : FirebaseFirestore
    private lateinit var mail : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_groups)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        data = arrayListOf()
        groupAdapter = GroupAdapter(data)
        recyclerView.adapter = GroupAdapter
        eventChangeListener()
    }
    private fun eventChangeListener() {
        db = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(false)
            .build()
        db.firestoreSettings = settings
        val user = Firebase.auth.currentUser?.uid.toString()
        db.collection("Users").get().addOnSuccessListener {
            for (document in it) {
                val uid = document["user"].toString()
                val email = document["email"].toString()
                if (user == uid) {
                    mail = email
                    break
                }
            }
        }
        db.collection("Groups")
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    val id = document.id
                    if (mail == id){
                        data.add(Groups())
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }
}