package com.example.mteamproject

import android.graphics.Bitmap
import java.util.*

data class Art(var artID:Int,var artwork:Bitmap?,var title:String, val artist:Artist?, var categorycode:Int,var price:Int,var updatedate:Date?, var soldout:Boolean,var Action:Boolean,var auctionstartdate:Date?,var auctionenddate:Date?){
    constructor():this(0,null,"",null,0,0,null,false,false,null,null)
}
