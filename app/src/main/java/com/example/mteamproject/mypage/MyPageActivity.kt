package com.example.mteamproject.mypage

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mteamproject.main.MainActivity
import com.example.mteamproject.R
import com.example.mteamproject.databinding.ActivityMyPageBinding


class MyPageActivity : AppCompatActivity() {
    lateinit var binding:ActivityMyPageBinding
    val mpViewModel: MPViewModel by viewModels()
    lateinit var myDBHelper:MyDBHelper
    val mainFragment = MPMainFragment()
    val loadFragment = MPLoadFragment()
    val soldFragment = MPSoldFragment()
    val likeFragment = MPLikeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initFragment()

    }

    private fun initFragment(){
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.fragmentView, mainFragment)
        fragment.commit()
    }
    private fun init(){
        myDBHelper = MyDBHelper(this)

        binding.homeBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}