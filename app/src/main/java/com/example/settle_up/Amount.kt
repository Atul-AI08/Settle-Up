package com.example.settle_up

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Amount : AppCompatActivity() {
    private lateinit var group : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amount)
        group = intent.getStringExtra("name").toString()
        findViewById<Button>(R.id.button1).setOnClickListener {
            val amount = findViewById<EditText>(R.id.amount).text.toString()
            if(isNumeric(amount)){
                val intent = Intent(this, ChooseUser::class.java)
                intent.putExtra("amount", amount)
                intent.putExtra("name", group)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Enter numerical value", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun isNumeric(toCheck: String): Boolean {
        val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
        return toCheck.matches(regex)
    }
}