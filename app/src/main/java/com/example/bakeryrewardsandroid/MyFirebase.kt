package com.example.bakeryrewardsandroid

import android.content.Context
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MyFirebase(context: Context) {
    var auth : FirebaseAuth = FirebaseAuth.getInstance()
    var user : FirebaseUser = auth.currentUser!!
    var database: FirebaseDatabase = Firebase.database
    var context = context
//    fun checkUser():Boolean{
//        if(auth.currentUser != null){
//            return true
//        }
//        return false
//    }
    fun setPointsToView(textView: TextView){
        val reference = database.getReference("users")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("USER: " + snapshot.child("points").value)
                textView.text = snapshot.child(user.uid).child("points").value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                println("ERROR")
            }
        })
    }
//    fun removePoints(value: Int){
//        val reference = database.getReference("users")
//        reference.child(user.uid).child("points").get().addOnSuccessListener {
//            Log.i("firebase", "Got value ${it.value}")
//            var p = it.value.toString()
//            if (p.toInt() >= value){
//                reference.child(user.uid).child("points").setValue(p.toInt()-value)
//            }
//            else{
//                Log.e("firebase","Not enough points")
//                Toast.makeText(context,"NOT ENOUGH POINTS",Toast.LENGTH_SHORT).show()
//            }
//
//        }.addOnFailureListener{
//            Log.e("firebase", "Error getting data", it)
//        }
//    }

    fun addPoints(value: Int){
        val reference = database.getReference("users")
        reference.child(user.uid).child("points").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            var p = it.value.toString()
            reference.child(user.uid).child("points").setValue(p.toInt()+value)
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

}