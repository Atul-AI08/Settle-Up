package com.example.settle_up

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase

class ChooseAmount : AppCompatActivity() {
    private lateinit var db : FirebaseFirestore
    private lateinit var group : String
    private lateinit var customAdapter: AmountAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var data : ArrayList<Details>
    private var selected : ArrayList<String>? = null
    private lateinit var email : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_amount)
        db = FirebaseFirestore.getInstance()
        data = arrayListOf()
        val amount = hashMapOf<String, Any>()
        group = intent.getStringExtra("name").toString()
        val money = intent.getStringExtra("amount").toString()
        selected = intent.getStringArrayListExtra("users")
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        customAdapter = AmountAdapter(data, amount)
        recyclerView.adapter = customAdapter
        eventChangeListener()
        val user = Firebase.auth.currentUser?.uid.toString()
        db.collection("Users").document(user).get().addOnSuccessListener {
            email = it["email"].toString()
        }
        findViewById<Button>(R.id.button1).setOnClickListener {
            var sum = 0
            for (key in amount.keys) {
                sum += amount[key].toString().toInt()
            }
            if (money.toInt() == sum) {
                for (key in amount.keys) {
                    Log.d("abc", key)
                    db.collection("Groups").document(group).collection("Users").document(key).get()
                        .addOnSuccessListener { document ->
                            val history = hashMapOf<String, Any>()
                            val nkey = "$email -> $key"
                            history[nkey] = amount[key].toString()
                            val m = document["amount"].toString().toInt()
                            amount[key] = amount[key].toString().toInt() + m
                            val change = hashMapOf<String, Any>()
                            change["amount"] = amount[key].toString()
                            db.collection("History").document(group).set(history, SetOptions.merge())
                            db.collection("Groups").document(group).collection("Users").document(key)
                                .update(change).addOnCompleteListener {
                                    val intent = Intent(this, ShowGroups::class.java)
                                    startActivity(intent)
                                }
                        }
                }
            }else{
                    Toast.makeText(this, "Sum not equal to entered amount", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun eventChangeListener() {
        db = FirebaseFirestore.getInstance()
        for(i in 0 until selected!!.size){
            db.collection("Groups").document(group).collection("Users").get().addOnSuccessListener {
                for (document in it){
                    val email = document["email"].toString()
                    val name = "Name: " + document["name"].toString()
                    val amount = "0"
                    if (email == selected!![i]){
                        data.add(Details(name, email, amount))
                        customAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}