package com.example.empoweringthenationfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SewingActivity : AppCompatActivity() {

    private val courseFee = 1500 // Fee for the Sewing course

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sewing)

        // Find views
        val selectSewingCheckbox: CheckBox = findViewById(R.id.select_sewing_course)
        val calculateButton: Button = findViewById(R.id.calculate_button)
        val resultTextView: TextView = findViewById(R.id.result_text_view)
        val backButton: Button = findViewById(R.id.back_button)

        val homeButton: Button = findViewById(R.id.home_button)
        val coursesButton: Button = findViewById(R.id.courses_button)
        val aboutUsButton: Button = findViewById(R.id.about_button)
        val contactButton: Button = findViewById(R.id.contact_button)

        // click listener on the Back button to navigate back
        backButton.setOnClickListener {
            finish() // Finishes the current activity and goes back to the previous screen
        }

        // listener on the checkbox to add or remove the course from the selected courses
        selectSewingCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Add the course to the selected courses
                SelectedCoursesManager.addCourse("Sewing")
                Toast.makeText(this, "Sewing course added to cart", Toast.LENGTH_SHORT).show()
            } else {
                // Remove the course from the selected courses
                SelectedCoursesManager.removeCourse("Sewing")
                Toast.makeText(this, "Sewing course removed from cart", Toast.LENGTH_SHORT).show()
            }
        }

        // onClickListener on the calculate button
        calculateButton.setOnClickListener {
            val selectedCourses = SelectedCoursesManager.getSelectedCourses()

            if (selectedCourses.isNotEmpty()) {
                // Calculate the total fee based on the number of selected courses
                var totalFee = selectedCourses.size * courseFee

                // Apply discounts based on the number of courses selected
                when (selectedCourses.size) {
                    2 -> totalFee = (totalFee * 0.95).toInt() // 5% discount for 2 courses
                    3 -> totalFee = (totalFee * 0.90).toInt() // 10% discount for 3 courses
                    else -> if (selectedCourses.size > 3) totalFee = (totalFee * 0.85).toInt() // 15% discount for more than 3 courses
                }

                // Add VAT (15%) to the total
                val totalWithVAT = (totalFee * 1.15).toInt()

                // Update the resultTextView with the calculated total
                resultTextView.text = getString(R.string.total_with_vat, totalWithVAT)
            } else {
                // If no course is selected, show a message
                Toast.makeText(this, "Please select at least one course", Toast.LENGTH_SHORT).show()
            }
        }
        // Home button - navigate to the first screen
        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Courses button - navigate to the third screen (CoursesActivity)
        coursesButton.setOnClickListener {
            val intent = Intent(this, CoursesActivity::class.java)
            startActivity(intent)
        }

        // About Us button - refresh or stay on the current AboutUsActivity
        aboutUsButton.setOnClickListener {
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }

        // Contact Us button - navigate to the fourth screen (ContactActivity, which we'll create)
        contactButton.setOnClickListener {
            val intent = Intent(this, ContactActivity::class.java)
            startActivity(intent)
        }
    }
}


