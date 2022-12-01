package com.example.settle_up

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.merge

class CreateGroup : AppCompatActivity() {
    private lateinit var etName : String
    private lateinit var auth: FirebaseAuth
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)
        auth = Firebase.auth
        findViewById<Button>(R.id.create).setOnClickListener {
            val data = hashMapOf<String, Any>()
            val user = Firebase.auth.currentUser?.uid.toString()
            etName = findViewById<EditText>(R.id.name).text.toString()
            db.collection("Users").document(user).get().addOnSuccessListener { document ->
                val email = document["email"].toString()
                val name = document["name"].toString()
                data["email"] = email
                data["name"] = name
                data["amount"] = 0
                db.collection("Groups").document(etName).collection("Users").document(email).set(data).addOnCompleteListener{
                    Toast.makeText(this, "Group Created", Toast.LENGTH_SHORT).show()
                    val groups = hashMapOf<String, Any>()
                    groups[etName] = 0
                    db.collection("UserGroups").document(email).set(groups, SetOptions.merge()).addOnCompleteListener{
                        val intent = Intent(this, ShowGroups::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}