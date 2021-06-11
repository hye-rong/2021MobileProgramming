package com.example.mteamproject.mypage

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mteamproject.R
import com.example.mteamproject.databinding.MysoldrowBinding


class SoldAdapter(val items:MutableList<Product>): RecyclerView.Adapter<SoldAdapter.MyViewHolder>(){


    inner class MyViewHolder(val binding: MysoldrowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = MysoldrowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            //pimage.setImageURI(items[position].pPic)

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}