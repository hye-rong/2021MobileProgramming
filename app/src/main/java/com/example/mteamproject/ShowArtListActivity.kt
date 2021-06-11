package com.example.mteamproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mteamproject.databinding.ActivityShowArtListBinding

class ShowArtListActivity : AppCompatActivity() {
    lateinit var binding:ActivityShowArtListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowArtListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}