package com.example.mteamproject.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mteamproject.R
import com.example.mteamproject.databinding.ActivityAsdBinding
import com.example.mteamproject.databinding.ActivityProductTestBinding

class ProductTest : AppCompatActivity() {
    lateinit var binding: ActivityProductTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){
        val intent = intent
        val aid = intent.getStringExtra("aId")
        val apid = intent.getStringExtra("apId")
        binding.apply {
            button2.text = aid
            button3.text = apid
        }
    }
}