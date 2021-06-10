package com.example.mteamproject.mypage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mteamproject.MainActivity
import com.example.mteamproject.R
import com.example.mteamproject.databinding.ActivityMyPageBinding


class MyPageActivity : AppCompatActivity() {
    lateinit var binding:ActivityMyPageBinding
    val mainFragment = MPMainFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFragment()
        init()
    }

    private fun initFragment(){
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.fragmentView, mainFragment)
        fragment.commit()
    }
    private fun init(){
        binding.homeBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    fun changeFragment(index: Int) {
        when (index) {
            0 -> supportFragmentManager.beginTransaction().replace(R.id.fragmentView, mainFragment)
                .addToBackStack(null)
                .commit()
            //1 -> supportFragmentManager.beginTransaction().replace(R.id.fragmentView, fragment_menu)
            //    .commit()
        }
    }
}