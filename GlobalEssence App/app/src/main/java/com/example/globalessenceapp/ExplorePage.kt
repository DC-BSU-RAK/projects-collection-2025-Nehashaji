package com.example.globalessenceapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ExplorePage : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_explore_page)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.exploreScroll)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setting click listeners for all buttons by their ids
        val buttonIds = listOf(
            R.id.viewMoreUSA,
            R.id.viewMoreUAE,
            R.id.viewMoreCanada,
            R.id.viewMoreIndia,
            R.id.viewMoreJapan,
            R.id.viewMoreAustralia,
            R.id.viewMoreUK,
            R.id.viewMoreEgypt,
            R.id.viewMorePhilippines,
            R.id.viewMoreBrazil
        )

        buttonIds.forEach { id ->
            findViewById<Button>(id)?.setOnClickListener(this)
        }
    }

    override fun onClick(view: View?) {
        view ?: return

        // Map button IDs to country names
        val countryMap = mapOf(
            R.id.viewMoreUSA to "United States",
            R.id.viewMoreUAE to "United Arab Emirates",
            R.id.viewMoreCanada to "Canada",
            R.id.viewMoreIndia to "India",
            R.id.viewMoreJapan to "Japan",
            R.id.viewMoreAustralia to "Australia",
            R.id.viewMoreUK to "United Kingdom",
            R.id.viewMoreEgypt to "Egypt",
            R.id.viewMorePhilippines to "Philippines",
            R.id.viewMoreBrazil to "Brazil"
        )

        val countryName = countryMap[view.id] ?: return

        // Intent to open CountryDetailActivity
        val intent = Intent(this, CountryDetailActivity::class.java)
        intent.putExtra("country_name", countryName)
        startActivity(intent)
    }
}
