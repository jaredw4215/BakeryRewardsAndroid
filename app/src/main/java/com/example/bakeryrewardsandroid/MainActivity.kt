package com.example.bakeryrewardsandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    lateinit var auth : FirebaseAuth
    lateinit var logoutButton : Button
    lateinit var tempTV : TextView
    lateinit var user : FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        logoutButton = findViewById(R.id.logout)
        tempTV = findViewById(R.id.tempTV)
        user = auth.currentUser!!
        if (user == null){
            val intent : Intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            tempTV.text = user.email
        }

        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent : Intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}