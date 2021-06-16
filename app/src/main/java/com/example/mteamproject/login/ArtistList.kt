package com.example.mteamproject.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mteamproject.databinding.ActivityArtistListBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class ArtistList : AppCompatActivity() {
    lateinit var binding: ActivityArtistListBinding
    lateinit var adapter: ArtistListAdapter
    lateinit var rdb: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init() {
        rdb = FirebaseDatabase.getInstance().getReference("UserDB/Artist")

        val query = rdb.orderByChild("likeNum")
        val option =  FirebaseRecyclerOptions.Builder<Artist1>().setQuery(query, Artist1::class.java).build()
        adapter = ArtistListAdapter(option)
        adapter.itemClickListener = object : ArtistListAdapter.OnItemClickListener {
            override fun OnItemClick(view: View, position: Int) {
                val intent = Intent(this@ArtistList, ArtistPage::class.java)
                intent.putExtra("aId", adapter.getItem(position).aId.toString())
                intent.putExtra("aName", adapter.getItem(position).aName.toString())
                intent.putExtra("likeNum", adapter.getItem(position).likeNum.toString().toInt())
                intent.putExtra("soldNum", adapter.getItem(position).soldNum.toString().toInt())
                intent.putExtra("loadNum", adapter.getItem(position).loadNum.toString().toInt())
                intent.putExtra("aSpec", adapter.getItem(position).aSpec.toString())
                startActivity(intent)
            }
        }
        binding.apply {
            artistRecyclerView.adapter = adapter
            homeBtn.setOnClickListener {
                val intent = Intent(this@ArtistList, UserLogin::class.java)
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