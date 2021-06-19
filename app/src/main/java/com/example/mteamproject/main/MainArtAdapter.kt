package com.example.mteamproject.main

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mteamproject.databinding.MainArtRowBinding
import com.example.mteamproject.databinding.SimpleAucRowBinding
import com.example.mteamproject.mypage.Product
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage

class MainArtAdapter(var items:MutableList<Product>): RecyclerView.Adapter<MainArtAdapter.MyViewHolder>(){


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
            downloadImg(items[position].pPic, holder)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
    private fun downloadImg(imgloc: String, holder: MainArtAdapter.MyViewHolder) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReference()
        val submitImg = storageRef.child(imgloc)
        submitImg.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
            override fun onSuccess(p0: Uri?) {
                Glide.with(holder.binding.root)
                    .load(p0)
                    .into(holder.binding.imageView)
            }
        })
    }

}