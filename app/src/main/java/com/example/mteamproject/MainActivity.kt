package com.example.mteamproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mteamproject.databinding.ActivityMainBinding
import com.example.mteamproject.enroll.EnrollPage

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        val uID = intent.getStringExtra("uId").toString()
        binding.apply {
            enrollbtn.setOnClickListener {
                val intent = Intent(applicationContext, EnrollPage:: class.java)
                intent.putExtra("uID", uID)
                startActivity(intent)
            }
        }
    }
}