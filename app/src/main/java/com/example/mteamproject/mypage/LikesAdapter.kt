package com.example.mteamproject.mypage

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mteamproject.ArtLitAdapater
import com.example.mteamproject.databinding.MypagerowBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage

class LikesAdapter(val items:MutableList<Product>): RecyclerView.Adapter<LikesAdapter.MyViewHolder>(){


    interface OnItemClickListener{
        fun OnItemClick(holder: RecyclerView.ViewHolder, data: Product, pos:Int)
        fun OnLikeClick(holder: RecyclerView.ViewHolder, data: Product, pos:Int)
    }

    var itemClickListener:OnItemClickListener?=null

    inner class MyViewHolder(val binding: MypagerowBinding): RecyclerView.ViewHolder(binding.root){

        init{
            binding.itemView.setOnClickListener {
                itemClickListener?.OnItemClick(this,items[adapterPosition], adapterPosition)
            }
            binding.likeBtn.setOnClickListener {
                itemClickListener?.OnLikeClick(this,items[adapterPosition], adapterPosition)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = MypagerowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            downloadImg(items[position].pPic ,holder)
            pName.text = items[position].pId
            pauthor.text = items[position].pAuthor
            pprice.text = items[position].pPrice.toString()

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun downloadImg(imgloc: String, holder: LikesAdapter.MyViewHolder) {
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