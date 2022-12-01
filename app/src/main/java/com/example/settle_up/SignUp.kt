package com.example.settle_up

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {
    private lateinit var etEmail : String
    private lateinit var etPass : String
    private lateinit var etCnfPass : String
    private lateinit var auth: FirebaseAuth
    private lateinit var etName : String
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = Firebase.auth
        findViewById<Button>(R.id.signup).setOnClickListener {
            val email = findViewById<EditText>(R.id.email).text.toString()
            val pass = findViewById<EditText>(R.id.pass).text.toString()
            val cnfpass = findViewById<EditText>(R.id.cnfpass).text.toString()
            val name = findViewById<EditText>(R.id.name).text.toString()
            etEmail = email
            etPass = pass
            etCnfPass = cnfpass
            etName = name
            signUpUser()
        }
        findViewById<TextView>(R.id.signin).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun signUpUser() {
        val email = etEmail
        val pass = etPass
        val cnfpass = etCnfPass
        val name = etName
        if (email.isBlank() || pass.isBlank()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
        }
        else if (name.isEmpty()) {
            Toast.makeText(this, "Enter your name.", Toast.LENGTH_SHORT).show()
        }
        else if (pass != cnfpass)
            Toast.makeText(this, "Password and Confirm Password are not same", Toast.LENGTH_SHORT).show()
        else {
            auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val uid = Firebase.auth.currentUser?.uid.toString()
                    val data = hashMapOf<String, Any>()
                    data["name"] = name
                    data["email"] = email
                    db.collection("Users").document(uid).set(data).addOnCompleteListener{
                        val intent = Intent(this, HomePage::class.java)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this, "Sign Up Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}