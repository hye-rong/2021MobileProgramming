package com.example.mteamproject.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mteamproject.R
import com.example.mteamproject.databinding.ActivityArtistListBinding
import com.example.mteamproject.databinding.ActivityArtistPageBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*

class ArtistPage : AppCompatActivity() {
    lateinit var binding: ActivityArtistPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){
        val intent = intent
        val aid = intent.getStringExtra("aId")
        val aname = intent.getStringExtra("aName")
        val linum = intent.getIntExtra("likeNum", -1)
        val sonum = intent.getIntExtra("soldNum", -1)
        val lonum = intent.getIntExtra("loadNum", -1)
        val aspec = intent.getStringExtra("aSpec")


        binding.apply {
            artistId.text = aname
            likeNum.text = linum.toString()
            soldNum.text = sonum.toString()
            loadNum.text = lonum.toString()
            artistSpec.text = aspec

            homeBtn.setOnClickListener {
                val intent = Intent(this@ArtistPage, UserLogin::class.java)
                startActivity(intent)
            }

            artistProductBtn.setOnClickListener {
                val intent = Intent(this@ArtistPage, ArtistProduct::class.java)
                intent.putExtra("aId", aid)
                startActivity(intent)
            }
        }
    }
}