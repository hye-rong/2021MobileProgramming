package com.example.mteamproject

import android.graphics.Bitmap
import java.io.Serializable
import java.util.*

data class Art(var artwork:String?,var title:String, val artist:Artist?, var category:Int,var price:Int,var Auction:Boolean,var auctionenddate:String?, var key:String):Serializable{
    constructor():this(null,"",null,0,0,false,null,"")
}
