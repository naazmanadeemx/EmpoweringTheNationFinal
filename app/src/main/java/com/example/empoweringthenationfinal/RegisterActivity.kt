package com.example.empoweringthenationfinal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val nameEditText: EditText = findViewById(R.id.name)
        val mobileNumberEditText: EditText = findViewById(R.id.mobile_number)
        val emailEditText: EditText = findViewById(R.id.email)
        val passwordEditText: EditText = findViewById(R.id.register_password)
        val confirmPasswordEditText: EditText = findViewById(R.id.confirm_password)

        val registerButton: Button = findViewById(R.id.register_button)

        // click listener for the Register button
        registerButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val mobileNumber = mobileNumberEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            // Validate input fields
            if (!validateName(name)) {
                nameEditText.error = "Please enter a valid name"
            } else if (!validateMobileNumber(mobileNumber)) {
                mobileNumberEditText.error = "Please enter a valid mobile number"
            } else if (!validateEmail(email)) {
                emailEditText.error = "Please enter a valid email address"
            } else if (!validatePassword(password, confirmPassword)) {
                passwordEditText.error = "Passwords do not match or are too short"
                confirmPasswordEditText.error = "Passwords do not match or are too short"
            } else {
                // If validation is successful, save data and navigate to the login screen
                val sharedPreferences = getSharedPreferences("UserPref", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("name", name)
                editor.putString("mobile_number", mobileNumber)
                editor.putString("email", email)
                editor.putString("password", password)
                editor.apply()

                Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()

                // Navigate to MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    // Validate that the name is not empty
    private fun validateName(name: String): Boolean {
        return name.isNotEmpty()
    }

    // Validate the mobile number
    private fun validateMobileNumber(mobileNumber: String): Boolean {
        return mobileNumber.isNotEmpty() && mobileNumber.length >= 10 && mobileNumber.all { it.isDigit() }
    }

    // Validate email format
    private fun validateEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Validate password and confirm password
    private fun validatePassword(password: String, confirmPassword: String): Boolean {
        return password.length >= 6 && password == confirmPassword
    }
}
