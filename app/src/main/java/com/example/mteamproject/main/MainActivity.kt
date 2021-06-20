package com.example.mteamproject.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mteamproject.R
import com.example.mteamproject.ShowArtListActivity
import com.example.mteamproject.databinding.ActivityMainBinding
import com.example.mteamproject.enroll.EnrollPage
import com.example.mteamproject.login.ArtistEnroll
import com.example.mteamproject.login.ArtistList
import com.example.mteamproject.login.UserLogin
import com.example.mteamproject.mypage.MyPageActivity
import com.example.mteamproject.mypage.Product
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: AuctionRcAdapter
    lateinit var adapter2: MainArtAdapter
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var aucList:ArrayList<Product>
    lateinit var mainList:ArrayList<Product>
    lateinit var rdb: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPreferences.edit()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initLayout()
    }

    fun init(){
        aucList = intent.getSerializableExtra("auctionList") as ArrayList<Product>
        mainList = intent.getSerializableExtra("mainList") as ArrayList<Product>

        adapter = AuctionRcAdapter(aucList)
        adapter2 = MainArtAdapter(mainList)
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity,
                LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = adapter

            viewpager.adapter = adapter2
            TabLayoutMediator(tabLayout, viewpager){
                    _, _ ->

            }.attach()
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
                R.id.art_menu ->{
                    //전체 작품 보기 Activity로 이동
                    val intent = Intent(this, ShowArtListActivity::class.java)
                    startActivity(intent)
                }
                R.id.artist_menu ->{
                    //작가 소개 Activity로 이동
                    val intent = Intent(this, ArtistList::class.java)
                    startActivity(intent)
                }

                R.id.home_menu ->{
                    // 홈화면
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.mypage_menu ->{
                    val uID = intent.getStringExtra("uId").toString()
                    val intent = Intent(this, MyPageActivity::class.java)
                    intent.putExtra("uId", uID)
                    startActivity(intent)
                }
                R.id.upload_menu ->{
                    // 작품 upload로 이동
                    if(sharedPreferences.getBoolean("isArtist", false)) {
                        val uID = intent.getStringExtra("uId").toString()
                        val intent = Intent(this, EnrollPage::class.java)
                        intent.putExtra("uId", uID)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@MainActivity, "먼저 작가로 등록하시길 바랍니다", Toast.LENGTH_SHORT).show()
                    }

                }
                R.id.load_artist->{
                    val uid = sharedPreferences.getString("Id", "")
                    var arr = arrayListOf<Any>()

                    rdb = FirebaseDatabase.getInstance().getReference("UserDB/User")
                    rdb.child(uid.toString())
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                            }
                            override fun onDataChange(p0: DataSnapshot) {
                                var num = 0
                                for (snapshot in p0.children) {
                                    arr.add(snapshot.value.toString())
                                    num++
                                    if(num == 9)
                                        break
                                }
                                if(arr[0].toString().toBoolean()){
                                    Toast.makeText(this@MainActivity, "작가로 등록되어 있습니다", Toast.LENGTH_SHORT).show()
                                }else {
                                    val intent = Intent(this@MainActivity, ArtistEnroll::class.java)
                                    intent.putExtra("uId", arr[6].toString())
                                    intent.putExtra("uName", arr[7].toString())
                                    intent.putExtra("uAge", arr[5].toString().toInt())
                                    startActivity(intent)
                                }
                            }
                        })
                }
                R.id.logout -> {
                    //로그아웃 기능
                    editor.putBoolean("AutoLogin", false)
                    editor.putBoolean("isArtist", false)
                    editor.apply()
                    editor.putString("Id", "")
                    editor.putString("Password","")
                    editor.commit()
                    val intent = Intent(this, UserLogin::class.java)
                    startActivity(intent)
                }
            }

            true
        }
        binding.apply {
            art1.setOnClickListener {
                //풍경화 List 보여지게
                val intent = Intent(this@MainActivity, ShowArtListActivity::class.java)
                intent.putExtra("category", 1)
                startActivity(intent)
            }
            art2.setOnClickListener {
                //추상화 List 보여지게
                val intent = Intent(this@MainActivity, ShowArtListActivity::class.java)
                intent.putExtra("category", 2)
                startActivity(intent)
            }
            art3.setOnClickListener {
                //동양화 List 보여지게
                val intent = Intent(this@MainActivity, ShowArtListActivity::class.java)
                intent.putExtra("category", 3)
                startActivity(intent)
            }
            art4.setOnClickListener {
                //서양화 List 보여지게
                val intent = Intent(this@MainActivity, ShowArtListActivity::class.java)
                intent.putExtra("category", 4)
                startActivity(intent)
            }
            artListBtn.setOnClickListener {
                val intent = Intent(this@MainActivity, ShowArtListActivity::class.java)
                startActivity(intent)
            }

        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("eoeoeo", "onOptionsItemSelected")
        Log.d("eoeoeo", "${item.itemId}")
        Log.d("eoeoeo", "${R.id.home}")

        binding.drawerLayout.openDrawer(GravityCompat.START)
        return true
    }

}