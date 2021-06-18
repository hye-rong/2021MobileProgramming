package com.example.mteamproject

import android.graphics.Bitmap
import java.util.*

data class Art(var artwork:Bitmap?,var title:String, val artist:Artist?, var category:Int,var price:Int,var Action:Boolean,var auctionenddate:String?){
    constructor():this(null,"",null,0,0,false,null)
}
