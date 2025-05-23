package com.example.globalessenceapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Delay for 5 seconds (5000 ms)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@MainActivity, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }
}
