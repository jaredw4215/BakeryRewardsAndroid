package com.example.bakeryrewardsandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {
    lateinit var emailET : TextInputEditText
    lateinit var passwordET : TextInputEditText
    lateinit var password2ET : TextInputEditText
    lateinit var submitButton: Button
    lateinit var auth : FirebaseAuth
    lateinit var progressBar : ProgressBar

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent : Intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()
        emailET = findViewById(R.id.emailET)
        passwordET = findViewById(R.id.passwordET)
        password2ET = findViewById(R.id.password2ET)
        submitButton = findViewById(R.id.submit)
        progressBar = findViewById(R.id.progress)

        submitButton.setOnClickListener {
            val email : String = emailET.text.toString()
            val password : String = passwordET.text.toString()
            val password2 : String = password2ET.text.toString()

            if (email.isEmpty() || password.isEmpty() || password2.isEmpty()){
                Toast.makeText(this,"One of the fields is empty",Toast.LENGTH_SHORT).show()
            }
            else if (password != password2){
                Toast.makeText(this,"Passwords do not match",Toast.LENGTH_SHORT).show()
            }
            else{
                progressBar.visibility = View.VISIBLE
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            progressBar.visibility = View.GONE
                            val user = auth.currentUser!!
                            val database = Firebase.database
                            val myRef = database.getReference(user.uid)
                            myRef.child("email").setValue(user.email)
                            myRef.child("points").setValue(120)
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(
                                this,
                                "Account Created.",
                                Toast.LENGTH_SHORT,
                            ).show()
                            val intent : Intent = Intent(this,LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            progressBar.visibility = View.GONE
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                this,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }


        }
    }
}