package com.example.mteamproject

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.*
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mteamproject.databinding.ActivityShowArtListBinding
import com.example.mteamproject.login.ArtistList
import com.example.mteamproject.login.UserLogin
import com.example.mteamproject.main.MainActivity
import com.example.mteamproject.main.SplashActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.okhttp.internal.DiskLruCache
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
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
        if (intent!=null){
            var cat = intent.getIntExtra("category",0);

            when(cat){
                0->{
                    binding.spinner.setSelection(0)

                }
                1->binding.spinner.setSelection(1)
                2->binding.spinner.setSelection(2)
                3->binding.spinner.setSelection(3)
                else->binding.spinner.setSelection(3)
            }
        }
    }
    private fun initData(){
        val intent = intent
        val aid = intent.getStringExtra("aId")

        if(aid.toString().length >= 1)
            db = FirebaseDatabase.getInstance().getReference("Art/$aid")
        if(aid.toString() == "null")
            db = FirebaseDatabase.getInstance().getReference("ArtRCV")

        db.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var children = snapshot.children
                if(snapshot.childrenCount>0) {
                    for (i in children.iterator()) {
                        //Log.i("data","${i.value as HashMap<String,String>}")
                        Log.d("eoeoeo", "key: ${i.key}, ref: ${i.ref}")
                        val key = i.key
                        val value = i.value as HashMap<String,String>
                        val imageUrl =value["imageUrl"] as String

                        var artwork = null
                        try {
                            val url = URL(imageUrl)
                            var connection = url.openConnection() as HttpURLConnection
                            connection.connect()
                            val nSize = connection.contentLength
                            val bis = BufferedInputStream(connection.getInputStream(), nSize)
                            artwork = BitmapFactory.decodeStream(bis) as Nothing?
                            bis.close()
                            connection.disconnect()
                        } catch (e: Exception) {
                            Log.i("data", e.toString())
                        }
                        val title = value["title"] as String
                        val artist_name = value["userID"] as String
                        val artist = Artist(artist_name, null)
                        val cat = value["genre"] as String
                        val catcode: Int = when (cat) {
                            "풍경" -> 0
                            "추상화" -> 1
                            "서양화" -> 2
                            "동양화" -> 3
                            else -> 4
                        }
                        val price = value["sellPrice"] as Long
                        val auction = value["ifauction"] as Boolean
                        val edate = value["endDate"].toString()
                        //val art = Art(artwork, title, artist, catcode, price.toInt(), auction, edate)
//                        val art = Art(imageUrl, title, artist, catcode, price.toInt(), auction, edate)
                        val art = Art(imageUrl, title, artist, catcode, price.toInt(), auction, edate, key!!)
                        arts1.add(art)
                        when (catcode) {
                            0-> {arts2.add(art);adapter2.notifyItemInserted(arts2.size-1)}
                            1 -> {arts3.add(art);adapter3.notifyItemInserted(arts3.size-1)}
                            else -> {arts4.add(art);adapter4.notifyItemInserted(arts4.size-1)}
                        }
                        Log.i("data",art.toString())
                        adapter1.notifyItemInserted(arts1.size-1)


                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //TODO("Not yet implemented")
            }

        })
    }
    private fun init(){
        val layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.apply {
            Log.i("init","init")
            adapter1 = ArtLitAdapater(arts1)
            var listener=object : ArtLitAdapater.OnArtClickListener{
                override fun itemClicked(
                    holder: ArtLitAdapater.ViewHolder,
                    view: View,
                    item: Art,
                    pos: Int,
                ) {
                    val intent = Intent(this@ShowArtListActivity,ArtDetail::class.java)
                    intent.putExtra("title",item.title)
                    intent.putExtra("artist",item.artist?.name)
                    intent.putExtra("cat",item.category)
                    intent.putExtra("price",item.price)
                    intent.putExtra("auction",item.Auction)
                    intent.putExtra("endauction",item.auctionenddate)
                    intent.putExtra("art",item.artwork)
                    intent.putExtra("key", item.key)
                    startActivity(intent)
                }

            }
            adapter2 = ArtLitAdapater(arts2)
            adapter3 = ArtLitAdapater(arts3)
            adapter4 = ArtLitAdapater(arts4)
            adapter1.listener = listener
            adapter2.listener = listener
            adapter3.listener = listener
            adapter4.listener = listener
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

        }
    }
}