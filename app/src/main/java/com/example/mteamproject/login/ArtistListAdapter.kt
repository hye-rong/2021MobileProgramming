package com.example.mteamproject.login

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mteamproject.databinding.ArtistrowBinding
import com.example.mteamproject.mypage.LikesAdapter
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage


class ArtistListAdapter (options: FirebaseRecyclerOptions<User>)
    : FirebaseRecyclerAdapter<User, ArtistListAdapter.ViewHolder>(options){

    interface OnItemClickListener{
        fun OnItemClick(view: View, position: Int)
    }

    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(var binding: ArtistrowBinding): RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener{
                itemClickListener!!.OnItemClick(it, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ArtistrowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }




    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: User) {
        holder.binding.apply {
            artistId.text = model.uName
            likeNum.text = model.likeNum.toString()
            loadNum.text = model.loadNum.toString()
            downloadImg(model.zImgUrl ,holder)
        }
    }
    private fun downloadImg(imgloc: String, holder: ViewHolder) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReference()
        val submitImg = storageRef.child(imgloc)
        submitImg.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
            override fun onSuccess(p0: Uri?) {
                Glide.with(holder.binding.root)
                    .load(p0)
                    .into(holder.binding.artistImg)
            }
        })
    }
}