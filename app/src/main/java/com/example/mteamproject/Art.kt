package com.example.mteamproject

import android.graphics.Bitmap
import java.util.*

data class Art(var artwork:Bitmap?,var title:String, val artistID:Int, var categorycode:Int,var price:Int,val updatedate:Date?, var soldout:Boolean,val Action:Boolean,val auctionstartdate:Date?,var auctionenddate:Date?){
    constructor():this(null,"",0,0,0,null,false,false,null,null)
}
