package com.example.mteamproject.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mteamproject.R
import com.example.mteamproject.databinding.ActivityArtistPageBinding
import com.example.mteamproject.databinding.ActivityArtistProductBinding
import com.example.mteamproject.main.MainActivity
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ArtistProduct : AppCompatActivity() {
    lateinit var binding: ActivityArtistProductBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: ArtistProductListAdapter
    lateinit var rdb: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun init(){
        val intent = intent
        val aid = intent.getStringExtra("aId")

        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rdb = FirebaseDatabase.getInstance().getReference("UserDB/User")

        val query = rdb.child(aid.toString()).child("zArtList")
        val option =  FirebaseRecyclerOptions.Builder<Product1>().setQuery(query, Product1::class.java).build()
        adapter = ArtistProductListAdapter(option)
        adapter.itemClickListener = object : ArtistProductListAdapter.OnItemClickListener {
            override fun OnItemClick(view: View, position: Int) {
                val intent = Intent(this@ArtistProduct, ProductTest::class.java)
                intent.putExtra("aId", aid)
                intent.putExtra("apId", adapter.getItem(position).pId)
                startActivity(intent)
            }
        }
        binding.apply {
            artistProductRecyclerView.layoutManager = layoutManager
            artistProductRecyclerView.adapter = adapter
            homeBtn.setOnClickListener {
                val intent = Intent(this@ArtistProduct, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}