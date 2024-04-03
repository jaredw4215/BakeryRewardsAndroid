package com.example.bakeryrewardsandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    lateinit var pointsTV : TextView
    lateinit var recycler: RecyclerView
    lateinit var myadapter: HomeItemAdapter
    lateinit var displayCodeTV: TextView
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private var user : FirebaseUser = auth.currentUser!!
    private var database: FirebaseDatabase = Firebase.database

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v =  inflater.inflate(R.layout.fragment_home, container, false)
        val myRef = database.getReference("HomeItems")
//        myRef.child("222457").setValue(ItemData("Bread","25",""))
//        myRef.child("223457").setValue(ItemData("Cookie","10",""))
//        myRef.child("224457").setValue(ItemData("Cupcake","45",""))

        recycler = v.findViewById(R.id.recyclerView)
        recycler.layoutManager = LinearLayoutManager(v.context)
        recycler.setHasFixedSize(true)
        myadapter = HomeItemAdapter()
        recycler.adapter = myadapter

        myRef.get().addOnSuccessListener {
            val itemList : ArrayList<ItemData> = arrayListOf()
            for (i in it.children){
                itemList.add(ItemData(
                    i.child("name").value.toString(),
                    i.child("cost").value.toString(),
                    i.child("path").value.toString()
                ))
            }
            myadapter.sumbitList(itemList)
        }

        pointsTV = v.findViewById(R.id.pointsTV)
        displayCodeTV = v.findViewById(R.id.displayCodeTV)
        setPointsToView(pointsTV)
        //setCodeToView(displayCodeTV)
        return v
    }

    private fun setPointsToView(textView: TextView){
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

//    private fun setCodeToView(textView: TextView){
//        val reference = database.getReference("users")
//        reference.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val data = snapshot.child(user.uid).child("code").value.toString()
//                if (!(data.isEmpty())){
//                    textView.visibility = View.VISIBLE
//                    textView.text = "Your Code: " + data
//                }
//                else{
//                    textView.visibility = View.GONE
//                }
//            }
//            override fun onCancelled(error: DatabaseError) {
//                println("ERROR")
//            }
//        })
//    }
}