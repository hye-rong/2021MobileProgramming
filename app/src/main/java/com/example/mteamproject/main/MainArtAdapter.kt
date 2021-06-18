package com.example.mteamproject.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mteamproject.databinding.MainArtRowBinding
import com.example.mteamproject.databinding.SimpleAucRowBinding
import com.example.mteamproject.mypage.Product

class MainArtAdapter(val items:MutableList<Product>): RecyclerView.Adapter<MainArtAdapter.MyViewHolder>(){


    inner class MyViewHolder(val binding: MainArtRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = MainArtRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            artView.text = items[position].pId
            artistView.text = items[position].pAuthor
            //imageView.setImageURI(items[position].pPic)
            //titleView.text = items[position].pId
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}