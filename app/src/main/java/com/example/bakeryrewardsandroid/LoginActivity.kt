package com.example.bakeryrewardsandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var emailET : TextInputEditText
    lateinit var passwordET : TextInputEditText
    lateinit var signupButton : TextView
    lateinit var submitButton: Button
    lateinit var progressBar : ProgressBar
    lateinit var auth : FirebaseAuth

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
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        emailET = findViewById(R.id.emailET)
        passwordET = findViewById(R.id.passwordET)
        signupButton = findViewById(R.id.signupButton)
        submitButton = findViewById(R.id.submit)
        progressBar = findViewById(R.id.progress)

        signupButton.setOnClickListener {
            val intent : Intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }
        submitButton.setOnClickListener {
            val email: String = emailET.text.toString()
            val password: String = passwordET.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "One of the fields is empty", Toast.LENGTH_SHORT).show()
            }
            else {
                progressBar.visibility = View.VISIBLE
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            progressBar.visibility = View.GONE
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(
                                this,
                                "Login Successful.",
                                Toast.LENGTH_SHORT,
                            ).show()
                            val intent : Intent = Intent(this,MainActivity::class.java)
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