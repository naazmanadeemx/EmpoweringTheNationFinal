package com.example.empoweringthenationfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class AboutUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        val viewCoursesButton: Button = findViewById(R.id.view_courses_button)

        // OnClickListener on the "View our courses" button
        viewCoursesButton.setOnClickListener {
            // Create an Intent to start CoursesActivity
            val intent = Intent(this, CoursesActivity::class.java)
            startActivity(intent)
        }

        // Set up bottom navigation buttons as Button, not LinearLayout
        val homeButton: Button = findViewById(R.id.home_button)
        val coursesButton: Button = findViewById(R.id.courses_button)
        val aboutUsButton: Button = findViewById(R.id.about_button)
        val contactButton: Button = findViewById(R.id.contact_button)

        val aboutUsText: TextView = findViewById(R.id.about_us_text)

        aboutUsText.text =
            "Our mission is to empower individuals through education and skill development. " +
                    "We believe that knowledge is the key to unlocking potential and transforming lives. " +
                    "Our platform offers a wide range of courses designed to cater to diverse learning needs. \n\n" +
                    "At Empowering The Nation, we strive to create an inclusive environment that fosters creativity and innovation. " +
                    "Our experienced instructors are dedicated to providing high-quality education, utilizing the latest teaching methods and technologies. \n\n" +
                    "Whether you are looking to enhance your skills, change your career path, or pursue personal development, we have the right courses for you. " +
                    "We understand that everyone has unique goals, and we are here to support you every step of the way.\n\n" +
                    "Join us on this journey of growth and self-improvement. Together, we can build a brighter future! " +
                    "Explore our courses today and take the first step towards achieving your dreams!"

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
