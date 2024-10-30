package com.example.empoweringthenationfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val cartTextView: TextView = findViewById(R.id.courses_list_text_view)
        val totalAmountTextView: TextView = findViewById(R.id.total_amount_text_view)
        val discountMessageTextView: TextView = findViewById(R.id.discount_message_text_view)
        val confirmButton: Button = findViewById(R.id.confirm_button)
        val clearButton: Button = findViewById(R.id.clear_button)
        val checkoutButton: Button = findViewById(R.id.checkout_button)
        val addMoreCoursesButton: Button = findViewById(R.id.add_more_button)

        // Selected courses from the previous activity
        val selectedCourses = SelectedCoursesManager.getSelectedCourses()

        // Display the selected courses in the cart
        if (selectedCourses.isNotEmpty()) {
            val coursesList = selectedCourses.joinToString(separator = "\n")
            cartTextView.text = coursesList

            // Calculate the total fees based on selected courses
            val courseFee = 1500.0 // Assuming each course has a flat fee
            var totalFee = selectedCourses.size * courseFee

            // Apply discounts based on the number of courses selected
            val discountPercentage = when (selectedCourses.size) {
                1 -> 0.0
                2 -> 0.05
                3 -> 0.10
                else -> 0.15
            }

            // Calculate discounted total
            val discountedAmount = totalFee * (1 - discountPercentage)

            // Add VAT (15%) to the total
            val totalWithVAT = discountedAmount * 1.15
            totalAmountTextView.text = "Total (with VAT): R%.2f".format(totalWithVAT)

            // Set discount message based on the number of selected courses
            val discountMessage = when (selectedCourses.size) {
                2 -> "Since you selected 2 courses, you will get 5% off when checking out!"
                3 -> "Since you selected 3 courses, you will get 10% off when checking out!"
                in 4..Int.MAX_VALUE -> "Since you selected more than 3 courses, you will get 15% off when checking out!"
                else -> ""
            }
            discountMessageTextView.text = discountMessage

            // Set up the proceed to checkout button action
            checkoutButton.setOnClickListener {
                if (selectedCourses.isNotEmpty()) {
                    val intent = Intent(this, CheckoutActivity::class.java)
                    intent.putExtra("total_amount", totalWithVAT) // Pass the total amount to CheckoutActivity
                    intent.putExtra("selected_courses", ArrayList(selectedCourses))
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "No courses to proceed with!", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            cartTextView.text = "No courses selected"
            totalAmountTextView.text = "Total (with VAT): R0.00"
            discountMessageTextView.text = ""
        }

        // Confirm button action
        confirmButton.setOnClickListener {
            if (selectedCourses.isNotEmpty()) {
                Toast.makeText(this, "Courses confirmed!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No courses to confirm", Toast.LENGTH_SHORT).show()
            }
        }

        // Clear button action
        clearButton.setOnClickListener {
            if (selectedCourses.isNotEmpty()) {
                SelectedCoursesManager.clearCourses()
                cartTextView.text = "No courses selected"
                totalAmountTextView.text = "Total (with VAT): R0.00"
                discountMessageTextView.text = ""
                Toast.makeText(this, "Cart cleared!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No courses to clear", Toast.LENGTH_SHORT).show()
            }
        }

        // Add more courses button action
        addMoreCoursesButton.setOnClickListener {
            val intent = Intent(this, CoursesActivity::class.java)
            startActivity(intent)
        }
    }
}
