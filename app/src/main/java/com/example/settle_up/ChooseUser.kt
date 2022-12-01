package com.example.settle_up

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ChooseUser : AppCompatActivity() {
    private lateinit var recyclerView : RecyclerView
    private lateinit var customAdapter: ChooseAdapter
    private lateinit var data : ArrayList<Details>
    private lateinit var db : FirebaseFirestore
    private lateinit var group : String
    private lateinit var selected : ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_user)
        group = intent.getStringExtra("name").toString()
        val amount = intent.getStringExtra("amount").toString()
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        data = arrayListOf()
        selected = arrayListOf()
        customAdapter = ChooseAdapter(data, selected)
        recyclerView.adapter = customAdapter
        eventChangeListener()
        findViewById<Button>(R.id.button1).setOnClickListener {
            val intent = Intent(this, ChooseAmount::class.java)
            intent.putExtra("users", selected)
            intent.putExtra("name", group)
            intent.putExtra("amount", amount)
            startActivity(intent)
        }
    }
    private fun eventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("Groups").document(group).collection("Users").get().addOnSuccessListener {
            for (document in it){
                val email = "Email: " + document["email"].toString()
                val name = "Name: " + document["name"].toString()
                val amount = "0"
                data.add(Details(name, email, amount))
                customAdapter.notifyDataSetChanged()
            }
        }
    }
}