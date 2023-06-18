package com.example.bakeryrewardsandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.values
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    lateinit var auth : FirebaseAuth
    lateinit var user : FirebaseUser
    lateinit var pointsTV : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_home, container, false)
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!
        val database = Firebase.database
        val myRef = database.getReference(user.uid)
        val userPoints = myRef.child("points")
//        myRef.child("email").setValue(user.email)
//        myRef.child("points").setValue(120)
        pointsTV = v.findViewById(R.id.pointsTV)

//        userPoints.get().addOnSuccessListener {
//
//            pointsTV.text = it.value.toString()
//        }
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val points = dataSnapshot.value
                pointsTV.text = points.toString()
                // ...
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        userPoints.addValueEventListener(postListener)
        return v
    }
}