package com.example.globalessenceapp

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainPage : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userNameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        //  Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        //  Reference the user name TextView and updates welcome message
        userNameTextView = findViewById(R.id.userNameText)
        val userName = sharedPreferences.getString("NAME", null)

        if (userName != null) {
            userNameTextView.text = "Welcome, $userName!"
        } else {
            // Redirect to SignUpActivity after 5 seconds if user name not found
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, SignUpActivity::class.java))
                finish()
            }, 5000)
        }

        // Setting up instruction popup button
        findViewById<ImageButton>(R.id.instructButton).setOnClickListener {
            showInstructionPopup()
        }

        // Setting up country pin click listeners
        setupCountryPinListeners()

        //Setup for Bottom Navigation
        val bottomNav = findViewById<LinearLayout>(R.id.bottomNav)
        findViewById<ImageButton>(R.id.btnExplore).setOnClickListener {
            startActivity(Intent(this, ExplorePage::class.java))
        }

        findViewById<ImageButton>(R.id.btnHome).setOnClickListener {

        }

        findViewById<ImageButton>(R.id.btnProfile).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun setupCountryPinListeners() {
        val countryPins = mapOf(
            R.id.btn_usa to "USA",
            R.id.btn_uae to "UAE",
            R.id.btn_canada to "Canada",
            R.id.btn_india to "India",
            R.id.btn_japan to "Japan",
            R.id.btn_aus to "Australia",
            R.id.btn_uk to "UK",
            R.id.btn_egypt to "Egypt",
            R.id.btn_phil to "Philippines",
            R.id.btn_brazil to "Brazil"
        )

        for ((buttonId, countryName) in countryPins) {
            findViewById<View>(buttonId)?.setOnClickListener {
                openCountryDetails(countryName)
            }
        }
    }

    private fun openCountryDetails(countryName: String) {
        val intent = Intent(this, CountryDetailActivity::class.java)
        intent.putExtra("country_name", countryName)
        startActivity(intent)
    }

    private fun showInstructionPopup() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.activity_instruction_page)
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog.setCancelable(true)

        dialog.findViewById<Button>(R.id.btnClose)?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}