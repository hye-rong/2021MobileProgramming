package com.example.mteamproject.login

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mteamproject.ShowArtListActivity
import com.example.mteamproject.databinding.ActivityArtistListBinding
import com.example.mteamproject.main.MainActivity
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class ArtistList : AppCompatActivity() {
    lateinit var binding: ActivityArtistListBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: ArtistListAdapter
    lateinit var rdb: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init() {
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rdb = FirebaseDatabase.getInstance().getReference("UserDB/User")

        val query = rdb.orderByChild("artist").equalTo(true.toString().toBoolean())
        val option =  FirebaseRecyclerOptions.Builder<User>().setQuery(query, User::class.java).build()
        adapter = ArtistListAdapter(option)
        adapter.itemClickListener = object : ArtistListAdapter.OnItemClickListener {
            override fun OnItemClick(view: View, position: Int) {
                val intent = Intent(this@ArtistList, ArtistPage::class.java)
                intent.putExtra("aId", adapter.getItem(position).uId.toString())
                intent.putExtra("aName", adapter.getItem(position).uName.toString())
                intent.putExtra("likeNum", adapter.getItem(position).likeNum.toString().toInt())
                intent.putExtra("soldNum", adapter.getItem(position).soldNum.toString().toInt())
                intent.putExtra("loadNum", adapter.getItem(position).loadNum.toString().toInt())
                intent.putExtra("aSpec", adapter.getItem(position).aSpec.toString())
                startActivity(intent)
            }
        }
        binding.apply {
            artistRecyclerView.layoutManager = layoutManager
            artistRecyclerView.adapter = adapter
            homeBtn.setOnClickListener {
                val intent = Intent(this@ArtistList, MainActivity::class.java)
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