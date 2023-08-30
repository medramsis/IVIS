package com.example.ivis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var edtEmail: EditText
    private lateinit var btnReset: Button
    private lateinit var btnSignUp: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        edtEmail = findViewById(R.id.edt_email_reset)
        btnReset = findViewById(R.id.btn_reset)
        btnSignUp = findViewById(R.id.btnSignup)


        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }


        btnReset.setOnClickListener {
            if (edtEmail.text.toString().isEmpty()) {
                Toast.makeText(this, "please enter your email", Toast.LENGTH_SHORT).show()

            }
            else  {
                val email= edtEmail.text.toString()
                forgotPassword(email)
                }
        }
    }


    fun forgotPassword(email: String) {
        // Initialize the FirebaseAuth instance
        val auth = FirebaseAuth.getInstance()

        // Send a password reset email
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Password reset email sent.", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)


                } else {
                    Toast.makeText(this, "Error sending password reset email.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}