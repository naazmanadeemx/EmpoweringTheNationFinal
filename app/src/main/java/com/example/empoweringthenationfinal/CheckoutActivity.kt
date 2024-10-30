package com.example.empoweringthenationfinal

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CheckoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val totalAmountTextView: TextView = findViewById(R.id.total_amount_text_view)
        val makePaymentButton: Button = findViewById(R.id.make_payment_button)
        val paymentMethodGroup: RadioGroup = findViewById(R.id.payment_method_group)
        val emailInput: EditText = findViewById(R.id.email_input)

        // Retrieve selected courses from SelectedCoursesManager
        val selectedCourses = SelectedCoursesManager.getSelectedCourses()
        val totalAmount = calculateTotalWithDiscountAndVAT(selectedCourses)

        // Display the calculated total amount with discount and VAT
        totalAmountTextView.text = "Total Amount (with VAT): R%.2f".format(totalAmount)

        // Make Payment button action
        makePaymentButton.setOnClickListener {
            val enteredEmail = emailInput.text.toString().trim()
            val selectedPaymentMethodId = paymentMethodGroup.checkedRadioButtonId

            // Validate email
            if (!isValidEmail(enteredEmail)) {
                emailInput.error = "Please enter a valid email address"
                return@setOnClickListener
            }

            // Validate payment method selection
            if (selectedPaymentMethodId == -1) {
                Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Proceed if validations are successful
            val selectedPaymentMethod: RadioButton = findViewById(selectedPaymentMethodId)
            val paymentMethod = selectedPaymentMethod.text.toString()

            // Simulate payment process
            Toast.makeText(this, "Processing payment via $paymentMethod...", Toast.LENGTH_SHORT).show()

            // After simulating payment, move to confirmation screen
            val intent = Intent(this, PaymentConfirmationActivity::class.java)
            intent.putExtra("total_amount", totalAmount)
            intent.putExtra("payment_method", paymentMethod)
            startActivity(intent)
        }
    }

    // Function to validate email format
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Function to calculate total amount with discount and VAT based on selected courses
    private fun calculateTotalWithDiscountAndVAT(selectedCourses: List<String>): Double {
        val coursePriceMap = mapOf(
            "First Aid" to 1500.0,
            "Sewing" to 1500.0,
            "Landscaping" to 1500.0,
            "Life Skills" to 1500.0,
            "Child Minding" to 750.0,
            "Cooking" to 750.0,
            "Garden Maintenance" to 750.0
        )

        // Calculate subtotal based on selected courses
        var subtotal = 0.0
        for (course in selectedCourses) {
            subtotal += coursePriceMap[course] ?: 0.0
        }

        // Apply discount based on course count
        val discountPercentage = when (selectedCourses.size) {
            1 -> 0.0
            2 -> 0.05
            3 -> 0.10
            else -> 0.15
        }

        // Calculate discounted total
        val discountedAmount = subtotal * (1 - discountPercentage)

        // Apply VAT at 15%
        return discountedAmount * 1.15
    }
}
