package com.example.mteamproject

import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.mteamproject.databinding.ActivityArtDetailBinding
import com.example.mteamproject.databinding.DialogAuctionBinding
import com.example.mteamproject.databinding.DialogCoinBinding
import com.example.mteamproject.mypage.MyDBHelper
import com.example.mteamproject.mypage.Product
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.Serializable

class ArtDetail : AppCompatActivity() {
    lateinit var binding:ActivityArtDetailBinding
    lateinit var myDBHelper:MyDBHelper
    lateinit var product: Product
    lateinit var db: DatabaseReference
    var auction = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initBtn()
    }
    private fun init(){
        val intent = intent
        if(intent!=null){
            val title = intent.getStringExtra("title")
            val artwork= intent.getSerializableExtra("art")
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
            auction = intent.getBooleanExtra("auction",false)
            val enddate = "경매 종료일:"+intent.getStringExtra("endauction")
            binding.apply {
               // ArtDetailImage.setImageBitmap(artwork)
                downloadImg(artwork.toString())
                ArtDetailTitlte.text = title
                ArtDetailArtist.text = artist
                if(auction){
                    endDateView.visibility = View.VISIBLE
                    endDateView.text = enddate
                }

                ArtDetailPrice.text = price.toString()
                if(auction)
                    ArtDetailButton.setText("경매참여")
                else
                    ArtDetailButton.setText("구매하기")
                textView4.text=cat
            }
            product = Product(title.toString(), artist.toString(), artwork.toString(), price)
        }
    }

    private fun downloadImg(imgloc: String) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReference()
        val submitImg = storageRef.child(imgloc)
        submitImg.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
            override fun onSuccess(p0: Uri?) {
                Glide.with(binding.root)
                    .load(p0)
                    .into(binding.ArtDetailImage)
            }
        })
    }
    fun initBtn(){
        myDBHelper = MyDBHelper(this)
        db = FirebaseDatabase.getInstance().getReference("ArtRCV")
        val coin = myDBHelper.getCoin()
        binding.apply {
            likeBtn.isChecked = myDBHelper.findFavorite(product.pId)
            likeBtn.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked)
                    myDBHelper.insertFavoriteProduct(product)
                else
                    myDBHelper.deleteFavoriteProduct(product.pId)
            }

            ArtDetailButton.setOnClickListener {
                if(auction){
                    var price = 0
                    val dlgBinding = DialogAuctionBinding.inflate(layoutInflater)
                    val dlgBuilder = AlertDialog.Builder(this@ArtDetail)
                    dlgBuilder.setView(dlgBinding.root) //우리가 만든 dlg
                        .setPositiveButton("경매 참여"){
                                _,_ ->
                            price = dlgBinding.editText.text.toString().toInt()
                            if(coin<price){
                                Toast.makeText(this@ArtDetail, "돈 부족으로 충전 후 경매 참여가 가능합니다.",
                                    Toast.LENGTH_LONG).show()
                            }
                            else if(price<product.pPrice){
                                Toast.makeText(this@ArtDetail, "현재 경매가보다 낮은 금액이므로 참여가 불가능합니다.",
                                    Toast.LENGTH_LONG).show()
                            }
                            else{
                                Toast.makeText(this@ArtDetail, "\"${product.pId}\"작품의 경매 참여가 완료되었습니다.",
                                    Toast.LENGTH_LONG).show()
                                binding.ArtDetailPrice.text = price.toString()
                                val key = intent.getStringExtra("key")
                                if(key!=null){
                                    db.child(key)
                                        .child("sellPrice")
                                        .setValue(price)
                                }
                            }
                        }
                        .setNegativeButton("취소"){
                                _,_ ->
                        }
                        .show()
                }
                else{
                    if(coin<product.pPrice)
                        Toast.makeText(this@ArtDetail, "돈 부족으로 충전 후 구매가 가능합니다.",
                            Toast.LENGTH_LONG).show()
                    else{
                        Toast.makeText(this@ArtDetail, "\"${product.pId}\"작품의 구매가 완료되었습니다.",
                            Toast.LENGTH_LONG).show()
                        myDBHelper.insertCoin(coin-product.pPrice)
                        myDBHelper.insertPurchaseProduct(product)
                        //firebase에 price -1로 바꿔주기
                        val key = intent.getStringExtra("key")
                        if(key!=null){
                            db.child(key)
                                .child("sellPrice")
                                .setValue(-1)
                        }

                    }
                }
            }
        }
    }
}