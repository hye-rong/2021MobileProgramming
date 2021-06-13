package com.example.mteamproject

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mteamproject.databinding.ActivityShowArtListBinding
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class ShowArtListActivity : AppCompatActivity() {
    lateinit var binding:ActivityShowArtListBinding

    lateinit var db :DatabaseReference
    lateinit var adapter1:ArtLitAdapater
    lateinit var adapter2:ArtLitAdapater
    lateinit var adapter3:ArtLitAdapater
    lateinit var adapter4:ArtLitAdapater
    lateinit var arts1:ArrayList<Art>
    lateinit var arts2:ArrayList<Art>
    lateinit var arts3:ArrayList<Art>
    lateinit var arts4:ArrayList<Art>
    lateinit var data:ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowArtListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        init()
    }
    private fun initData(){
        db = FirebaseDatabase.getInstance().getReference("Art/items")
        var query = db.limitToLast(50)
        db.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val id = snapshot.child("artID") as Int
                for(i in 0..50)
                    if(arts1[i].artID==id){
                        arts1[i] = snapshot.getValue() as Art
                    }
            }

            override fun onCancelled(error: DatabaseError) {
                //TODO("Not yet implemented")
                Toast.makeText(applicationContext,error.toString(),Toast.LENGTH_SHORT).show()
            }
        })
        var data = db.get()
        data.addOnSuccessListener {
            val count = it.childrenCount
            var children = it.children
            var iter = children.iterator()
            for(i in iter){
                //var artID:Int,var artwork:Bitmap?,var title:String, val artist:Artist?, var categorycode:Int,var price:Int,var updatedate:Date?, var soldout:Boolean,var Action:Boolean,var auctionstartdate:Date?,var auctionenddate:Date?
                val id =i.child("id") as Int
                val artwork = i.child("artwork") as Bitmap
                val title = i.child("title") as String
                val artist_id = i.child("artist/id") as Int
                val artist_name = i.child("artist/name") as String
                val artist_icon = i.child("artist/icon") as Bitmap
                val artist = Artist(artist_id,artist_name,artist_icon)
                val catcode = i.child("categorycode") as Int
                val price = i.child("price") as Int
                val update =  i.child("updatedate") as Date
                val soldout = i.child("soldout") as Boolean
                val auction = i.child("auction") as Boolean
                val sdate = i.child("autionstartdate") as Date
                val edate = i.child("autionenddate") as Date
                val art = Art(id,artwork,title,artist,catcode,price,update,soldout,auction, sdate,edate)
                arts1.add(art)
                when(catcode){
                    0->arts2.add(art)
                    1->arts3.add(art)
                    2 -> arts4.add(art)
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
            recycler1.layoutManager = layoutManager
            recycler1.adapter = adapter1
            recycler2.layoutManager = layoutManager
            recycler2.adapter = adapter2
            recycler3.layoutManager = layoutManager
            recycler3.adapter = adapter3
            recycler4.layoutManager = layoutManager
            recycler4.adapter = adapter4
        }
    }
}