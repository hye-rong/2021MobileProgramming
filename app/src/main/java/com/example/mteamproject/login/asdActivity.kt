package com.example.mteamproject.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.mteamproject.R
import com.example.mteamproject.databinding.ActivityAsdBinding
import com.example.mteamproject.databinding.ActivityUserLoginBinding

class asdActivity : AppCompatActivity() {
    lateinit var binding: ActivityAsdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAsdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){
        val intent = intent
        val uid = intent.getStringExtra("uId")
        binding.apply {
            button.text = uid
        }
    }
}