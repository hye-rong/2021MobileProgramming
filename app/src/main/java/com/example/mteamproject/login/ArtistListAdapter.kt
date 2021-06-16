package com.example.mteamproject.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mteamproject.databinding.ArtistrowBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions


class ArtistListAdapter (options: FirebaseRecyclerOptions<Artist1>)
    : FirebaseRecyclerAdapter<Artist1, ArtistListAdapter.ViewHolder>(options){

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




    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Artist1) {
        holder.binding.apply {
            artistId.text = model.aName
            likeNum.text = model.likeNum.toString()
            soldNum.text = model.soldNum.toString()
            loadNum.text = model.loadNum.toString()
        }
    }
}