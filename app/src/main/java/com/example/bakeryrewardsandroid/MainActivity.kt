package com.example.bakeryrewardsandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.bakeryrewardsandroid.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var auth : FirebaseAuth
    lateinit var user : FirebaseUser
    lateinit var bottomNav : BottomNavigationView
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())
        auth = FirebaseAuth.getInstance()
        bottomNav = findViewById(R.id.bottomNav)

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.bottom_home -> replaceFragment(HomeFragment())
                R.id.bottom_profile -> replaceFragment(ProfileFragment())
                else ->{}
            }
            true
        }
        user = auth.currentUser!!

        val database = Firebase.database
        val myRef = database.getReference(user.uid)
        myRef.child("email").setValue(user.email)
        myRef.child("points").setValue(0)

    }
    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}

