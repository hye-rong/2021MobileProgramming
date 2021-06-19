package com.example.mteamproject

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mteamproject.databinding.ArtRowBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage

class ArtLitAdapater(val items:ArrayList<Art>) :RecyclerView.Adapter<ArtLitAdapater.ViewHolder>(){
    var listener:OnArtClickListener? = null
    interface OnArtClickListener{
        fun itemClicked(holder:ViewHolder,view:View, item:Art,pos: Int)
    }
    val cateory = arrayListOf<String>("풍경","추상화","서양화","동양화","기타")
    inner class ViewHolder(val binding: ArtRowBinding):RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                listener?.itemClicked(this,it,items[adapterPosition],adapterPosition)
            }
        }
    }

    interface ItemClickListener {
        fun onClick(view: View, pos: Int)
    }
    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =ArtRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                //LayoutInflater.from(parent.context).inflate(R.layout.art_row,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        //TODO("Not yet implemented")
        holder.binding.apply {
            if(items[pos].artwork!=null) {
                //imageViewArt.setImageBitmap(items[pos].artwork)
                downloadImg(items[pos].artwork!! ,holder)
            }
            if(items[pos].artist?.icon!=null)
                imageViewArtistIcon.setImageBitmap(items[pos].artist?.icon)
            textViewTitle.text = items[pos].title
            textViewArtist.text = items[pos].artist!!.name
            textViewCategory.text = cateory[items[pos].category]
            if(items[pos].Auction){
                val str = "경매 종료일:"+items[pos].auctionenddate
                textViewPrice.text = str
            }else {
                textViewPrice.text = items[pos].price.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        //TODO("Not yet implemented")
        return items.size
    }

    private fun downloadImg(imgloc: String, holder: ViewHolder) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReference()
        val submitImg = storageRef.child(imgloc)
        submitImg.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
            override fun onSuccess(p0: Uri?) {
                Glide.with(holder.binding.root)
                    .load(p0)
                    .into(holder.binding.imageViewArt)
            }
        })
    }
}