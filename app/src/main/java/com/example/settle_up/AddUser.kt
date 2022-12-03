package com.example.settle_up

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class AddUser : AppCompatActivity() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)
        val group =intent.getStringExtra("name").toString()
        findViewById<Button>(R.id.add).setOnClickListener{
            val data = hashMapOf<String, Any>()
            val email = findViewById<EditText>(R.id.email).text.toString()
            val name = findViewById<EditText>(R.id.name).text.toString()
            data["email"] = email
            data["name"] = name
            data["amount"] = 0
            db.collection("Groups").document(group).collection("Users").document(email).set(data)
                .addOnCompleteListener{
                Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show()
                val groups = hashMapOf<String, Any>()
                groups[group] = 0
                db.collection("UserGroups").document(email).set(groups, SetOptions.merge()).addOnCompleteListener{
                    val intent = Intent(this, ShowGroups::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}