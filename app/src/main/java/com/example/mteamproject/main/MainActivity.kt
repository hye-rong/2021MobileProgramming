package com.example.mteamproject

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mteamproject.databinding.ActivityMainBinding
import com.example.mteamproject.enroll.EnrollPage
import com.example.mteamproject.login.ArtistList
import com.example.mteamproject.mypage.MyPageActivity
import com.example.mteamproject.mypage.Product
import com.example.mteamproject.mypage.SoldAdapter
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter:AuctionRcAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
        initData()
    }

    fun initData(){
        // db에서 가져오도록 바꿔야함
        val aucList = arrayListOf<Product>(
            Product("111","111", Uri.parse("df"), 5000),
            Product("111","111", Uri.parse("df"), 5000),
            Product("111","111", Uri.parse("df"), 5000),
            Product("111","111", Uri.parse("df"), 5000),
            Product("111","111", Uri.parse("df"), 5000),
            Product("111","111", Uri.parse("df"), 5000),
            Product("111","111", Uri.parse("df"), 5000)
        )
        adapter = AuctionRcAdapter(aucList)

        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity,
                LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = adapter
        }
    }

    fun initLayout(){
        setSupportActionBar(binding.toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            binding.drawerLayout.closeDrawers()
            val title = menuItem.title.toString()
            when(menuItem.itemId){
                R.id.art_menu->{
                    //전체 작품 보기 Activity로 이동
                    val intent = Intent(this, ShowArtListActivity::class.java)
                    startActivity(intent)
                }
                R.id.artist_menu->{
                    //작가 소개 Activity로 이동
                    val intent = Intent(this, ArtistList::class.java)
                    startActivity(intent)
                }
                R.id.auction_menu->{
                    // 경매 작품 보기 Activity로 이동

                }
                R.id.home_menu->{
                    // 홈화면
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.mypage_menu->{
                    val intent = Intent(this, MyPageActivity::class.java)
                    startActivity(intent)
                }
                R.id.upload_menu->{
                    // 작품 upload로 이동
                    val intent = Intent(this, EnrollPage::class.java)
                    startActivity(intent)
                }
            }

            true
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("eoeoeo", "onOptionsItemSelected")
        Log.d("eoeoeo", "${item.itemId}")
        Log.d("eoeoeo", "${R.id.home}")
        Log.d("eoeoeo", "R.id.home")

        binding.drawerLayout.openDrawer(GravityCompat.START)
        return true
    }
}