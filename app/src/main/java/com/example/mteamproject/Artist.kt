package com.example.mteamproject

import android.graphics.Bitmap

data class Artist(val id:Int,val name:String,val icon:Bitmap?){
    constructor():this(0,"",null)
}

