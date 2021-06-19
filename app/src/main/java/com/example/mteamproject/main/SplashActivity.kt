package com.example.mteamproject.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.mteamproject.R
import com.example.mteamproject.databinding.ActivitySplashBinding
import com.example.mteamproject.enroll.EnrollPage
import com.example.mteamproject.mypage.Product
import com.google.firebase.database.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.HashMap

class SplashActivity : AppCompatActivity() {
    lateinit var db : DatabaseReference
    lateinit var binding: ActivitySplashBinding
    val aucList = arrayListOf<Product>()
    val mainList = arrayListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val textAnim = AnimationUtils.loadAnimation(this, R.anim.anim_splash_textview)
        binding.textView.startAnimation(textAnim)
        val imageAnim = AnimationUtils.loadAnimation(this, R.anim.anim_splash_imageview)
        binding.imageView.startAnimation(imageAnim)

        db = FirebaseDatabase.getInstance().getReference("ArtRCV")
        db.get().addOnSuccessListener {
            var children = it.children
            if (it.childrenCount > 0) {
                for (i in children.iterator()) {

                    val value = i.value as HashMap<String, String>
                    val auction = value["ifauction"] as Boolean
                    val title = value["title"] as String
                    val artist_name = value["userID"] as String
                    val imageUrl =value["imageUrl"] as String
                    val price = value["sellPrice"] as Long

                    if(auction){
                        var edate = value["endDate"].toString()
                        Log.d("eoeoeo", "$edate")
                        edate = edate.replace("[", "")
                        edate = edate.replace("]","")
                        edate = edate.replace(" ", "")
                        val el = edate.split(",")
                        val endDate = getDate(el[0].toInt(), el[1].toInt(),el[2].toInt(),el[3].toInt(),el[4].toInt(),0)

                        val now: Long = System.currentTimeMillis()

                        val date = Date(now)
                        Log.d("eoeoeo", "현재시간: $date")
                        Log.d("eoeoeo", "종료시간: $endDate")

                        if(endDate.after(date)){
                            Log.d("eoeoeo", "경매중")
                            aucList.add(Product(title, artist_name, imageUrl, price.toInt()))
                        }
                    }
                    else{
                        mainList.add(Product(title, artist_name, imageUrl, price.toInt()))
                    }


                }

            }
            GlobalScope.launch { // launch new coroutine in background and continue
                delay(2000)
                val uID = intent.getStringExtra("uId").toString()
                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.putExtra("uId", uID)
                intent.putExtra("auctionList", aucList)
                intent.putExtra("mainList", mainList)
                startActivity(intent)
                finish()
            }


        }
    }
    fun getDate(year:Int, month:Int, date:Int, hour:Int, minute:Int, second:Int):Date {

        Log.d("eoeoeo", "$year / $month / $date / $hour / $minute")
        val cal = Calendar.getInstance()

        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month-1)
        cal.set(Calendar.DATE, date)
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, minute)
        cal.set(Calendar.SECOND, second)

        return cal.getTime();

    }
}