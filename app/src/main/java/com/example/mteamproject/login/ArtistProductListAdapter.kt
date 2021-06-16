package com.example.mteamproject.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mteamproject.R
import com.example.mteamproject.databinding.ArtistrowBinding
import com.example.mteamproject.databinding.MypagerowBinding
import com.example.mteamproject.mypage.Product
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class ArtistProductListAdapter (options: FirebaseRecyclerOptions<Product1>)
    : FirebaseRecyclerAdapter<Product1, ArtistProductListAdapter.ViewHolder>(options){

    interface OnItemClickListener{
        fun OnItemClick(view: View, position: Int)
    }

    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(var binding: MypagerowBinding): RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener{
                itemClickListener!!.OnItemClick(it, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = MypagerowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }




    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Product1) {
        holder.binding.apply {
            pName.text = model.pName
            pauthor.text = model.pAuthor
            pprice.text = model.pPrice.toString()
            //pimage.setImageResource(model.pPic) // uri형태 어떻게 넣는지
        }
    }
}