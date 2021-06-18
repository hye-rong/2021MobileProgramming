package com.example.mteamproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mteamproject.databinding.SimpleAucRowBinding
import com.example.mteamproject.mypage.Product

class MainArtAdapter(val items:MutableList<Product>): RecyclerView.Adapter<MainArtAdapter.MyViewHolder>(){


    inner class MyViewHolder(val binding: SimpleAucRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = SimpleAucRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            //imageView.setImageURI(items[position].pPic)

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}