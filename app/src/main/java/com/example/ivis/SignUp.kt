package com.example.ivis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore


class SignUp : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var btnSignIn: TextView
    private lateinit var mAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)



        mAuth = FirebaseAuth.getInstance()

        edtName = findViewById(R.id.edt_nom)

        edtEmail = findViewById(R.id.edt_adresse_email)
        edtPassword = findViewById(R.id.edt_mdp)
        btnSignUp = findViewById(R.id.btn_creeer)
        btnSignIn = findViewById(R.id.btn_login)



        btnSignIn.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        btnSignUp.setOnClickListener{
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val firstname= edtName.text.toString()


            if (email.isNotEmpty() && password.isNotEmpty() && firstname.isNotEmpty()) {

                signUp(firstname,email, password);
            }
        }
    }

    private fun signUp(name: String ,email: String, password: String){

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){
            task ->
            if (task.isSuccessful){
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)


                        val intent = Intent(this@SignUp, Login::class.java)
                finish()
                        startActivity(intent)





            }
            else {
                Toast.makeText(this@SignUp, "une erreur de traitement est survenue", Toast.LENGTH_SHORT).show()
                mAuth.signOut()

            }
        }
    }

    private fun addUserToDatabase(name: String ,email: String, uid: String){
        databaseReference = FirebaseDatabase.getInstance().getReference()

        databaseReference.child("user").child(uid).setValue(User(name,email,uid)) 
    }

}