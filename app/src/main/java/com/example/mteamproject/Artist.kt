package com.example.mteamproject

import android.graphics.Bitmap

data class Artist(var id:Int,var name:String,var icon:Bitmap?){
    constructor():this(0,"",null)
}

