package com.example.empoweringthenationfinal

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        val homeButton: Button = findViewById(R.id.home_button)
        val coursesButton: Button = findViewById(R.id.courses_button)
        val aboutUsButton: Button = findViewById(R.id.about_button)
        val contactButton: Button = findViewById(R.id.contact_button)
        val logoutButton: Button = findViewById(R.id.logout_button)
        val enquiryButton: Button = findViewById(R.id.enquiry_button) // Enquiry Button
        val locationButton: Button = findViewById(R.id.location_button) // View Location Button

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

        // Contact Us button - navigate to the ContactActivity
        contactButton.setOnClickListener {
            val intent = Intent(this, ContactActivity::class.java)
            startActivity(intent)
        }

        // Log out button - clears user session and navigates to login or main screen
        logoutButton.setOnClickListener {
            // Clear user session
            val sharedPreferences: SharedPreferences =
                getSharedPreferences("user_session", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear() // Clears the session data
            editor.apply()

            // Redirect to the main activity or login activity
            val intent = Intent(this, MainActivity::class.java) // Or LoginActivity
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clears activity stack
            startActivity(intent)
            finish()
        }

        // Enquiry button functionality - show contact options
        enquiryButton.setOnClickListener {
            showContactOptions()
        }

        // View Location button functionality - show location options
        locationButton.setOnClickListener {
            showLocationOptions()
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
                    // Navigate to Settings Activity
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_profile -> {
                    // Navigate to Profile Activity
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    // Function to display contact options in a dialog
    private fun showContactOptions() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Contact Us")
        dialogBuilder.setMessage("You can contact us via phone or email.")

        // Add "Call" option
        dialogBuilder.setPositiveButton("Call") { _, _ ->
            val phoneIntent = Intent(Intent.ACTION_DIAL)
            phoneIntent.data = Uri.parse("tel:0120674488")
            try {
                startActivity(phoneIntent)
            } catch (e: Exception) {
                Toast.makeText(this, "No dialer found!", Toast.LENGTH_SHORT).show()
            }
        }

        // Add "Email" option
        dialogBuilder.setNegativeButton("Email") { _, _ ->
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:empoweringthenation@gmail.com")
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Enquiry - Empowering the Nation")
            try {
                startActivity(emailIntent)
            } catch (e: Exception) {
                Toast.makeText(this, "No email app found!", Toast.LENGTH_SHORT).show()
            }
        }

        // Show the dialog, then set button colors
        val dialog = dialogBuilder.create()
        dialog.show()

        // Set the color for the positive ("Call") and negative ("Email") buttons
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.blue))
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getColor(R.color.blue))
    }

    // Function to display location options in a dialog with Google Maps error handling
    private fun showLocationOptions() {
        val locations = arrayOf(
            "Braamfontein 222 Smit Street, Johannesburg, 2000",
            "169 Oxford Road, Johannesburg, 2196",
            "Bedfordview AMR 3 Office Park, Johannesburg, 2008"
        )

        AlertDialog.Builder(this)
            .setTitle("Select a Location")
            .setItems(locations) { _, which ->
                val location = when (which) {
                    0 -> "geo:0,0?q=222 Smit Street, Braamfontein, Johannesburg"
                    1 -> "geo:0,0?q=169 Oxford Road, Johannesburg"
                    2 -> "geo:0,0?q=AMR 3 Office Park, Bedfordview, Johannesburg"
                    else -> null
                }

                if (location != null) {
                    val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(location))
                    mapIntent.setPackage("com.google.android.apps.maps")

                    // Check if Google Maps is installed
                    if (mapIntent.resolveActivity(packageManager) != null) {
                        try {
                            startActivity(mapIntent)
                        } catch (e: Exception) {
                            Toast.makeText(this, "Error opening Google Maps.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Google Maps app is not installed.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .show()
    }
}
