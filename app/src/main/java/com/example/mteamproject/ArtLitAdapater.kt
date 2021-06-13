package com.example.mteamproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mteamproject.databinding.ArtRowBinding

class ArtLitAdapater(val items:ArrayList<Art>) :RecyclerView.Adapter<ArtLitAdapater.ViewHolder>(){
    val cateory = arrayListOf<String>("풍경","추상화","기타")
    inner class ViewHolder(val binding: ArtRowBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =ArtRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                //LayoutInflater.from(parent.context).inflate(R.layout.art_row,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        //TODO("Not yet implemented")
        holder.binding.apply {
            imageViewArt.setImageBitmap(items[pos].artwork)
            imageViewArtistIcon.setImageBitmap(items[pos].artist!!.icon)
            textViewTitle.text = items[pos].title
            textViewArtist.text = items[pos].artist!!.name
            textViewCategory.text = cateory[items[pos].categorycode]
            textViewPrice.text = items[pos].price.toString()
        }
    }

    override fun getItemCount(): Int {
        //TODO("Not yet implemented")
        return items.size
    }
}