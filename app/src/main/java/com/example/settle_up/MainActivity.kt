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
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    private lateinit var btnSignUp: TextView
    private lateinit var tvRedirectLogin: Button
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etEmail = findViewById(R.id.email)
        etPass = findViewById(R.id.pass)
        btnSignUp = findViewById(R.id.signup)
        tvRedirectLogin = findViewById(R.id.signin)
        auth = Firebase.auth
        findViewById<TextView>(R.id.signup).setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
        btnSignUp.setOnClickListener {
            signUpUser()
        }

        tvRedirectLogin.setOnClickListener {
            login()
        }
        findViewById<TextView>(R.id.forgot).setOnClickListener {
            forgotPass()
        }
    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null) {
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }
    }
    private fun login() {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()
        if (email.isBlank() || pass.isBlank()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomePage::class.java)
                startActivity(intent)
            } else
                Toast.makeText(this, "Log In failed", Toast.LENGTH_SHORT).show()
        }
    }
    private fun signUpUser() {
        val intent = Intent(this, SignUp::class.java)
        startActivity(intent)
    }
    private fun forgotPass() {
        val email = etEmail.text.toString()
        if (email.isBlank()) {
            Toast.makeText(this, "Enter an email", Toast.LENGTH_SHORT).show()
            return
        }
        auth.sendPasswordResetEmail(etEmail.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email sent", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "Account not activated", Toast.LENGTH_SHORT).show()
                }
            }
    }
}