package com.example.mteamproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mteamproject.databinding.ActivityArtDetailBinding

class ArtDetail : AppCompatActivity() {
    lateinit var binding:ActivityArtDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}