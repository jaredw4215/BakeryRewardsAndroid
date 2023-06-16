package com.example.bakeryrewardsandroid

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlin.math.log

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    lateinit var tempTV : TextView
    lateinit var logoutButton : Button
    lateinit var auth : FirebaseAuth
    lateinit var user : FirebaseUser
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v : View = inflater.inflate(R.layout.fragment_profile,container,false)
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!
        tempTV = v.findViewById(R.id.tempTV)
        logoutButton = v.findViewById(R.id.logout)
        tempTV.text = user.email
        logoutButton.setOnClickListener {
            auth.signOut()
            val intent : Intent = Intent(activity,LoginActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }
        return v
    }
}