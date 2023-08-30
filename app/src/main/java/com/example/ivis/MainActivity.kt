package com.example.ivis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.OnCreateContextMenuListener
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

   /* private lateinit var btnPlus: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlus = findViewById(R.id.btn_plus)

        btnPlus.setOnClickListener {
            val intent = Intent(this, Plus::class.java)
            startActivity(intent)
        }

    } */

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var mAuth: FirebaseAuth

    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mDbRef: DatabaseReference

    


   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       setContentView(R.layout.contact_activity)

       userList= ArrayList()
       adapter= UserAdapter(this,userList)
       mAuth = FirebaseAuth.getInstance()
       mDbRef= FirebaseDatabase.getInstance().getReference()
       
       userRecyclerView =findViewById(R.id.userRecyclerView)
       userRecyclerView.layoutManager= LinearLayoutManager(this)

       userRecyclerView.adapter= adapter

        mDbRef.child("user").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()
                for ( postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(User::class.java)
                    if(mAuth.currentUser?.uid != currentUser?.uid){
                        userList.add(currentUser!!)

                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



   }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.logout){

            mAuth.signOut()

            return true
        }
        return true
    }

}