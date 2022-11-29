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
import com.google.firebase.ktx.Firebase

class CreateGroup : AppCompatActivity() {
    private lateinit var etName : String
    private lateinit var auth: FirebaseAuth
    private lateinit var mail : String
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)
        auth = Firebase.auth
        findViewById<Button>(R.id.create).setOnClickListener {
            val data = hashMapOf<String, Any>()
            val user = Firebase.auth.currentUser?.uid.toString()
            etName = findViewById<EditText>(R.id.name).text.toString()
            db.collection("Users").get().addOnSuccessListener {
                for (document in it) {
                    val uid = document["user"].toString()
                    val email = document["email"].toString()
                    if (user == uid) {
                        mail = email
                        data[mail] = 0
                        db.collection("Groups").document(etName).set(data).addOnCompleteListener{
                            Toast.makeText(this, "Group Created", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, AddUser::class.java)
                            startActivity(intent)
                        }
                        break
                    }
                }
            }
        }
    }
}