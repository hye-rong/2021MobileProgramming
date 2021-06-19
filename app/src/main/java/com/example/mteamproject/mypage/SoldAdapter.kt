package com.example.mteamproject.mypage

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mteamproject.ArtLitAdapater
import com.example.mteamproject.R
import com.example.mteamproject.databinding.MysoldrowBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage


class SoldAdapter(val items:MutableList<Product>): RecyclerView.Adapter<SoldAdapter.MyViewHolder>(){


    inner class MyViewHolder(val binding: MysoldrowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = MysoldrowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            downloadImg(items[position].pPic ,holder)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
    private fun downloadImg(imgloc: String, holder: SoldAdapter.MyViewHolder) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReference()
        val submitImg = storageRef.child(imgloc)
        submitImg.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
            override fun onSuccess(p0: Uri?) {
                Glide.with(holder.binding.root)
                    .load(p0)
                    .into(holder.binding.pimage)
            }
        })
    }

}