package com.example.empoweringthenationfinal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    // Firebase Authentication
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Find the Views
        val emailEditText: EditText = findViewById(R.id.email)
        val passwordEditText: EditText = findViewById(R.id.password)
        val loginButton: Button = findViewById(R.id.login_button)
        val forgotPasswordLink: TextView = findViewById(R.id.forgot_password)
        val registerLink: TextView = findViewById(R.id.register)
        val cantLoginLink: TextView = findViewById(R.id.cant_login)

        // Set a click listener on the login button
        loginButton.setOnClickListener {
            val enteredEmail = emailEditText.text.toString().trim()
            val enteredPassword = passwordEditText.text.toString().trim()

            // Input validation
            if (!isValidEmail(enteredEmail)) {
                emailEditText.error = "Enter a valid email address"
                emailEditText.requestFocus()
                return@setOnClickListener
            }

            if (enteredPassword.isEmpty()) {
                passwordEditText.error = "Password cannot be empty"
                passwordEditText.requestFocus()
                return@setOnClickListener
            }

            // Retrieve the stored email and password from SharedPreferences
            val sharedPreferences = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
            val savedEmail = sharedPreferences.getString("email", "")
            val savedPassword = sharedPreferences.getString("password", "")

            // Validate the email and password
            if (enteredEmail == savedEmail && enteredPassword == savedPassword) {
                // Successful login
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()

                // Navigate to the next screen (e.g., AboutUsActivity)
                val intent = Intent(this, AboutUsActivity::class.java)
                startActivity(intent)
            } else {
                // Show an error message for incorrect credentials
                Toast.makeText(this, "Invalid email or password.", Toast.LENGTH_SHORT).show()
            }
        }

        // Forgot Password functionality
        forgotPasswordLink.setOnClickListener {
            val email = emailEditText.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            } else if (!isValidEmail(email)) {
                Toast.makeText(this, "Enter a valid email address", Toast.LENGTH_SHORT).show()
            } else {
                // Send password reset email using Firebase Auth
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Reset link sent to your email", Toast.LENGTH_SHORT).show()
                        } else {
                            // More detailed error handling
                            val errorMessage = task.exception?.message ?: "Unknown error"
                            Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

        // Set click listener for register link
        registerLink.setOnClickListener {
            // Navigate to the RegisterActivity when "Register" is clicked
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Set click listener for "Can't Login?" link
        cantLoginLink.setOnClickListener {
            // Navigate to the HelpActivity
            val intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
        }
    }

    // Function to check if the email is valid
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
