package com.example.settle_up

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class GroupDetails : AppCompatActivity() {
    private lateinit var recyclerView : RecyclerView
    private lateinit var customAdapter: CustomAdapter
    private lateinit var data : ArrayList<Details>
    private lateinit var db : FirebaseFirestore
    private lateinit var group : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_details)
        group = intent.getStringExtra("name").toString()
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        data = arrayListOf()
        customAdapter = CustomAdapter(data)
        recyclerView.adapter = customAdapter
        eventChangeListener()
        findViewById<Button>(R.id.button1).setOnClickListener {
            val intent = Intent(this, AddUser::class.java)
            intent.putExtra("name", group)
            startActivity(intent)
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            val intent = Intent(this, ChooseUser::class.java)
            intent.putExtra("name", group)
            startActivity(intent)
        }
    }
    private fun eventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("Groups").document(group).collection("Users").get().addOnSuccessListener {
            for (document in it){
                val email = "Email: " + document["email"].toString()
                val name = "Name: " + document["name"].toString()
                val amount = "Amount: " + document["amount"].toString()
                data.add(Details(name, email, amount))
                customAdapter.notifyDataSetChanged()
            }
        }
    }
}