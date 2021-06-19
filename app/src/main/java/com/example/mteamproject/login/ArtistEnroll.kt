package com.example.mteamproject.login

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mteamproject.R
import com.example.mteamproject.databinding.ActivityArtistEnrollBinding
import com.example.mteamproject.databinding.ActivityArtistListBinding
import com.example.mteamproject.main.MainActivity
import com.google.firebase.database.*

class ArtistEnroll : AppCompatActivity() {
    lateinit var binding: ActivityArtistEnrollBinding
    lateinit var adapter: ArtistListAdapter
    lateinit var rdb: DatabaseReference
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistEnrollBinding.inflate(layoutInflater)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        setContentView(binding.root)
        init()
    }

    fun init(){
        val intent = intent
        val uid = intent.getStringExtra("uId")
        val uname = intent.getStringExtra("uName")
        val uage = intent.getIntExtra("uAge", -1)

        rdb = FirebaseDatabase.getInstance().getReference("UserDB/User")

        binding.apply {
            userID.text = uid.toString()
            userName.text = uname.toString()
            userAge.text = uage.toString()

            artistEnrollBtn.setOnClickListener {
                if(artistSpec.text.toString().length>=1) {
                    rdb.child(uid.toString()).child("artist").setValue(true.toString().toBoolean())
                    rdb.child(uid.toString()).child("aspec").setValue(artistSpec.text.toString())
                    Toast.makeText(this@ArtistEnroll, "작가 등록 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ArtistEnroll, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@ArtistEnroll, "이력을 작성해 주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }



    }
}