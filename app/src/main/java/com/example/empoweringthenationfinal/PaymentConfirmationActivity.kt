package com.example.empoweringthenationfinal

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PaymentConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_confirmation)

        val totalAmountTextView: TextView = findViewById(R.id.total_amount_text_view)
        val paymentMethodTextView: TextView = findViewById(R.id.payment_method_text_view)

        // Get the total amount and payment method from the previous activity
        val totalAmount = intent.getDoubleExtra("total_amount", 0.0)
        val paymentMethod = intent.getStringExtra("payment_method")

        // Display confirmation details
        totalAmountTextView.text = "Total Paid: R%.2f".format(totalAmount)
        paymentMethodTextView.text = "Payment Method: $paymentMethod"
    }
}
