package com.example.settle_up

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        findViewById<Button>(R.id.button1).setOnClickListener {
            val intent = Intent(this, CreateGroup::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            val intent = Intent(this, ShowGroups::class.java)
            startActivity(intent)
        }
    }
}