package com.example.settle_up

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class History : AppCompatActivity() {
    private lateinit var db : FirebaseFirestore
    private lateinit var data : ArrayList<String>
    private lateinit var group : String
    private lateinit var recyclerView : RecyclerView
    private lateinit var historyAdapter: HistoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        group = intent.getStringExtra("name").toString()
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        data = arrayListOf()
        historyAdapter = HistoryAdapter(data)
        recyclerView.adapter = historyAdapter
        eventChangeListener()
    }
    private fun eventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("History").document(group).get().addOnSuccessListener { document ->
            if (document != null) {
                val map = document.data
                if (map != null) {
                    for (s in map.keys){
                        val info = s + ": " + map[s].toString()
                        data.add(info)
                        historyAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}