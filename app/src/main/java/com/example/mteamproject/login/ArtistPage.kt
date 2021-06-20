package com.example.mteamproject.login

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mteamproject.R
import com.example.mteamproject.ShowArtListActivity
import com.example.mteamproject.databinding.ActivityArtistListBinding
import com.example.mteamproject.databinding.ActivityArtistPageBinding
import com.example.mteamproject.main.MainActivity
import com.example.mteamproject.main.SplashActivity
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

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
        val forimage = intent.getStringExtra("forImage")
        val linum = intent.getIntExtra("likeNum", -1)
        val lonum = intent.getIntExtra("loadNum", -1)
        val aspec = intent.getStringExtra("aSpec")

        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReference()
        val submitImg = storageRef.child(forimage.toString())
        submitImg.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
            override fun onSuccess(p0: Uri?) {
                Glide.with(binding.root)
                    .load(p0)
                    .into(binding.artistImg)
            }
        })

        binding.apply {
            downloadImg(forimage.toString())
            artistId.text = aname
            likeNum.text = linum.toString()
            loadNum.text = lonum.toString()
            artistSpec.text = aspec

            artistProductBtn.setOnClickListener {
                val intent = Intent(this@ArtistPage, ShowArtListActivity::class.java)
                intent.putExtra("aId", aid)
                startActivity(intent)
            }
        }
    }

    private fun downloadImg(imgloc: String) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReference()
        val submitImg = storageRef.child(imgloc)
        submitImg.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
            override fun onSuccess(p0: Uri?) {
                Glide.with(binding.root)
                    .load(p0)
                    .into(binding.artistImg)
            }
        })
    }
}