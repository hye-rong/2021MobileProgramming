package com.example.mteamproject

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mteamproject.databinding.ActivityShowArtListBinding
import com.example.mteamproject.login.ArtistList
import com.example.mteamproject.login.UserLogin
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.*
import com.squareup.okhttp.internal.DiskLruCache
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class ShowArtListActivity : AppCompatActivity() {
    lateinit var binding:ActivityShowArtListBinding

    lateinit var db :DatabaseReference
    lateinit var adapter1:ArtLitAdapater
    lateinit var adapter2:ArtLitAdapater
    lateinit var adapter3:ArtLitAdapater
    lateinit var adapter4:ArtLitAdapater
    var arts1:ArrayList<Art> = ArrayList()
    var arts2:ArrayList<Art> = ArrayList()
    var arts3:ArrayList<Art> = ArrayList()
    var arts4:ArrayList<Art> = ArrayList()
    var currentcategory = 0;

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    lateinit var data:ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowArtListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPreferences.edit()
        initData()
        init()
    }
    private fun initData(){
        db = FirebaseDatabase.getInstance().getReference("Art/usrID")
        var data = db.get().addOnCompleteListener{
            var children = it.result?.children
            if(it.result!=null&& it.result?.childrenCount!!>0) {
                if (children != null) {
                    for (i in children.iterator()) {
                        //var artID:Int,var artwork:Bitmap?,var title:String, val artist:Artist?, var categorycode:Int,var price:Int,var updatedate:Date?, var soldout:Boolean,var Action:Boolean,var auctionstartdate:Date?,var auctionenddate:Date?
                        val imageUrl = i.child("iamgeUrl") as URL
                        var connection=imageUrl.openConnection()
                        connection.connect()
                        val nSize = connection.contentLength
                        val bis = BufferedInputStream(connection.getInputStream(),nSize)
                        val artwork =BitmapFactory.decodeStream(bis) as Bitmap
                        bis.close()
                        val title = i.child("title") as String
                        val artist_name = i.child("userID") as String
                        val artist = Artist( artist_name, null)
                        val cat = i.child("genre") as String
                        val catcode:Int = when(cat){
                            "풍경"->0
                            "추상화"->1
                            "서양화"->2
                            "동양화"->3
                            else ->5
                        }
                        val price = i.child("sellPrice") as Int
                        val auction = i.child("ifauction") as Boolean
                        val edate = i.child("endDate") as String
                        val art = Art(artwork,title,artist,catcode,price,auction,edate)
                        arts1.add(art)
                        when (catcode) {
                            0 -> arts2.add(art)
                            1 -> arts3.add(art)
                            else -> arts4.add(art)
                        }
                    }
                }
            }
        }
        adapter1 = ArtLitAdapater(arts1)
        adapter2 = ArtLitAdapater(arts2)
        adapter3 = ArtLitAdapater(arts3)
        adapter4 = ArtLitAdapater(arts4)
    }
    private fun init(){
        val layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        binding.apply {
            val items =arrayOf("전체보기","풍경화","추상화","others")
            val spinnerAdaper = ArrayAdapter(this@ShowArtListActivity,R.layout.support_simple_spinner_dropdown_item,items)
            spinner.adapter = spinnerAdaper
            spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    when(position) {
                        0 -> {
                            recycler1.visibility = android.view.View.VISIBLE
                            recycler2.visibility = android.view.View.GONE
                            recycler3.visibility = android.view.View.GONE
                            recycler4.visibility = android.view.View.GONE
                        }
                        1->{
                            recycler1.visibility = View.GONE
                            recycler2.visibility = View.VISIBLE
                            recycler3.visibility = View.GONE
                            recycler4.visibility = View.GONE
                        }
                        2->{
                            recycler1.visibility = View.GONE
                            recycler2.visibility = View.GONE
                            recycler3.visibility = View.VISIBLE
                            recycler4.visibility = View.GONE
                        }
                        else->{
                            recycler1.visibility = View.GONE
                            recycler2.visibility = View.GONE
                            recycler3.visibility = View.GONE
                            recycler4.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    recycler1.visibility = View.VISIBLE
                    recycler2.visibility = View.GONE
                    recycler3.visibility = View.GONE
                    recycler4.visibility = View.GONE
                }

            }
            recycler1.layoutManager = layoutManager
            recycler1.adapter = adapter1
            recycler2.layoutManager =LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
            recycler2.adapter = adapter2
            recycler3.layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
            recycler3.adapter = adapter3
            recycler4.layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
            recycler4.adapter = adapter4
            logout.setOnClickListener {
                editor.putBoolean("AutoLogin", false)
                editor.apply()
                editor.putString("Id", "")
                editor.putString("Password","")
                editor.commit()
                val intent = Intent(this@ShowArtListActivity, UserLogin::class.java)
                startActivity(intent)
            }
            homeBtn.setOnClickListener {
                val intent = Intent(this@ShowArtListActivity, UserLogin::class.java)
                startActivity(intent)
            }
            showartistlist.setOnClickListener {
                val intnet = Intent(this@ShowArtListActivity,ArtistList::class.java)
                startActivity(intent)
            }
        }
    }
}