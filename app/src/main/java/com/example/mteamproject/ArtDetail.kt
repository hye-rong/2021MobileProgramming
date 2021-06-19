package com.example.mteamproject

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mteamproject.databinding.ActivityArtDetailBinding
import java.io.Serializable

class ArtDetail : AppCompatActivity() {
    lateinit var binding:ActivityArtDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    private fun init(){
        val intent = intent
        if(intent!=null){
            val title = intent.getStringExtra("title")
            val artwork= intent.getSerializableExtra("artwork")
            val artist = intent.getStringExtra("artist")
            val catcode = intent.getIntExtra("cat",0)
            val cat = when(catcode){
                0->"풍경화"
                1->"추상화"
                2->"서양화"
                3->"동양화"
                else->"기타"
            }
            val price = intent.getIntExtra("price",0)
            val auction = intent.getBooleanExtra("auction",false)
            val enddate = "경매 종료일:"+intent.getStringExtra("endauction")
            binding.apply {
               // ArtDetailImage.setImageBitmap(artwork)
                ArtDetailTitlte.text = title
                ArtDetailArtist.text = artist
                if(auction){
                    ArtDetailPrice.text = enddate
                }
                else{
                    ArtDetailPrice.text = price.toString()
                }
                textView4.text=cat
            }
        }
    }
}