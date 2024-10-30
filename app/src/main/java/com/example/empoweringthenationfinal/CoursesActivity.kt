package com.example.empoweringthenationfinal

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity

class CoursesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courses)

        val homeButton: Button = findViewById(R.id.home_button)
        val coursesButton: Button = findViewById(R.id.courses_button)
        val aboutUsButton: Button = findViewById(R.id.about_button)
        val contactButton: Button = findViewById(R.id.contact_button)

        // Button for First Aid Course
        val firstAidButton: Button = findViewById(R.id.first_aid_course)
        firstAidButton.setOnClickListener {
            val intent = Intent(this, FirstAidActivity::class.java)
            startActivity(intent)
        }

        // Button for Sewing
        val sewingCourseButton: Button = findViewById(R.id.sewing_course)
        sewingCourseButton.setOnClickListener {
            val intent = Intent(this, SewingActivity::class.java)
            startActivity(intent)
        }

        // Button for Landscaping
        val landscapingCourseButton: Button = findViewById(R.id.landscaping_course)
        landscapingCourseButton.setOnClickListener {
            val intent = Intent(this, LandscapingActivity::class.java)
            startActivity(intent)
        }

        // Button for Life Skills
        val lifeSkillsCourseButton: Button = findViewById(R.id.life_skills_course)
        lifeSkillsCourseButton.setOnClickListener {
            val intent = Intent(this, LifeSkillsActivity::class.java)
            startActivity(intent)
        }

        // Button for Child Minding
        val childMindingButton: Button = findViewById(R.id.child_minding_course)
        childMindingButton.setOnClickListener {
            val intent = Intent(this, ChildMindingActivity::class.java)
            startActivity(intent)
        }

        // Button for Cooking
        val cookingButton: Button = findViewById(R.id.cooking_course)
        cookingButton.setOnClickListener {
            val intent = Intent(this, CookingActivity::class.java)
            startActivity(intent)
        }

        // Button for Garden Maintenance
        val gardenMaintenanceButton: Button = findViewById(R.id.garden_maintenance_course)
        gardenMaintenanceButton.setOnClickListener {
            val intent = Intent(this, GardenMaintenanceActivity::class.java)
            startActivity(intent)
        }

        // Home button
        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Courses button
        coursesButton.setOnClickListener {
            val intent = Intent(this, CoursesActivity::class.java)
            startActivity(intent)
        }

        // About Us button
        aboutUsButton.setOnClickListener {
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }

        // Contact Us button
        contactButton.setOnClickListener {
            val intent = Intent(this, ContactActivity::class.java)
            startActivity(intent)
        }

        // Cart Button functionality - Navigates to CartActivity
        val cartButton: Button = findViewById(R.id.cart_button)
        cartButton.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            // Send the selected courses to CartActivity
            intent.putStringArrayListExtra("selected_courses", ArrayList(SelectedCoursesManager.getSelectedCourses()))
            startActivity(intent)
        }

        // Menu Icon functionality - Opens Popup Menu
        val menuIcon: ImageView = findViewById(R.id.menu_icon)
        menuIcon.setOnClickListener {
            showPopupMenu(it)
        }
    }

    // Function to show a Popup Menu when the menu icon is clicked
    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu_items, popupMenu.menu)

        // Add menu item click listener
        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_settings -> {
                    // Navigate to Settings Activity (you can create this activity later)
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_profile -> {
                    // Navigate to Profile Activity (you can create this activity later)
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}
