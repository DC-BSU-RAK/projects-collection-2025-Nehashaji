package com.example.globalessenceapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileImage: ImageView
    private lateinit var tvUserName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPassword: TextView
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        profileImage = findViewById(R.id.profile_image)
        tvUserName = findViewById(R.id.tvUserName)
        tvEmail = findViewById(R.id.tvEmail)
        tvPassword = findViewById(R.id.tvPassword)
        btnLogout = findViewById(R.id.btnLogout)

        // Loading user data from SharedPreferences using same keys as in SignUpActivity
        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val username = sharedPref.getString("NAME", "Guest User")
        val email = sharedPref.getString("EMAIL", "guest@example.com")
        val password = sharedPref.getString("PASSWORD", "No Password")

        tvUserName.text = username
        tvEmail.text = email
        tvPassword.text = password

        btnLogout.setOnClickListener {
            // Clears user session and redirect to signup screen
            sharedPref.edit().clear().apply()
            val intent = Intent(this, SignUpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
